/*
 * @Author: Zhe Chen
 * 
 * @Date: 2021-04-22 12:52:04
 * 
 * @LastEditors: Zhe Chen
 * 
 * @LastEditTime: 2021-05-14 21:53:46
 * 
 * @Description: 记录数据访问类
 */
package com.buaa.lookclipboard.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import com.buaa.commons.foundation.Lazy;
import com.buaa.commons.foundation.Ref;
import com.buaa.commons.foundation.function.ThrowingConsumer;
import com.buaa.commons.util.JsonUtil;
import com.buaa.commons.util.StringUtil;
import com.buaa.lookclipboard.dao.DataAccessCenter;
import com.buaa.lookclipboard.dao.IRecordDao;
import com.buaa.lookclipboard.model.Record;
import com.buaa.lookclipboard.model.RecordQueryCondition;
import com.buaa.lookclipboard.util.DataFormatUtil;
import com.buaa.lookclipboard.util.LocalDateTimeUtil;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javafx.scene.input.DataFormat;

/**
 * 记录数据访问类
 */
public final class RecordDao implements IRecordDao {
    private final static String TABLE_RECORDS = "records";
    private final static String COLUMN_ID = "id";
    private final static String COLUMN_DATA_FORMAT = "dataFormat";
    private final static String COLUMN_CREATED_TIME = "createdTime";
    private final static String COLUMN_MODIFIED_TIME = "modifiedTime";
    private final static String COLUMN_CONTENT = "content";
    private final static String COLUMN_ISPINNED = "isPinned";

    private final static Lazy<RecordDao> instance = new Lazy<>(() -> new RecordDao());

    /**
     * 获取记录数据访问类实例
     * 
     * @return 记录数据访问类实例
     */
    public static RecordDao getInstance() {
        return instance.getValue();
    }

    private final Map<String, Object> baseValueMap = Collections.unmodifiableMap(MapUtils.putAll(new HashMap<>(), new Object[][] 
    {
        {"TABLE_RECORDS", TABLE_RECORDS}, 
        {"COLUMN_ID", COLUMN_ID},
        {"COLUMN_DATA_FORMAT", COLUMN_DATA_FORMAT},
        {"COLUMN_CREATED_TIME", COLUMN_CREATED_TIME},
        {"COLUMN_MODIFIED_TIME", COLUMN_MODIFIED_TIME},
        {"COLUMN_CONTENT", COLUMN_CONTENT}, 
        {"COLUMN_ISPINNED", COLUMN_ISPINNED}
    }));

    private RecordDao() {

    }

    private int manipulateRecords(String sql, ThrowingConsumer<PreparedStatement, SQLException> paramsSetter) throws SQLException {
        int rowsCount = 0;

        PreparedStatement ps = DataAccessCenter.getInstance().prepareStatement(sql);
        if (paramsSetter != null) {
            paramsSetter.accept(ps);
        }
        rowsCount = ps.executeUpdate();
        ps.close();

        return rowsCount;
    }

    private List<Record> queryRecords(String sql, ThrowingConsumer<PreparedStatement, SQLException> paramsSetter) throws SQLException {
        List<Record> recordList = new ArrayList<>();

        PreparedStatement ps = DataAccessCenter.getInstance().prepareStatement(sql);
        if (paramsSetter != null) {
            paramsSetter.accept(ps);
        }
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Record record = new Record();
            record.setID(rs.getString(COLUMN_ID));
            record.setDataFormat(DataFormatUtil.fromJSON(rs.getString(COLUMN_DATA_FORMAT)));
            record.setCreatedTime(LocalDateTimeUtil.fromTimeStamp(rs.getTimestamp(COLUMN_CREATED_TIME)));
            record.setModifiedTime(LocalDateTimeUtil.fromTimeStamp(rs.getTimestamp(COLUMN_MODIFIED_TIME)));
            record.setContent(rs.getString(COLUMN_CONTENT));
            record.setIsPinned(rs.getBoolean(COLUMN_ISPINNED));

            recordList.add(record);
        }

        rs.close();
        ps.close();

        return Collections.unmodifiableList(recordList);
    }

    private int queryRecordsCount(String sql, ThrowingConsumer<PreparedStatement, SQLException> paramsSetter) throws SQLException {
        int rowsCount = 0;

        PreparedStatement ps = DataAccessCenter.getInstance().prepareStatement(sql);
        if (paramsSetter != null) {
            paramsSetter.accept(ps);
        }
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            rowsCount = rs.getInt(1);
        }

        rs.close();
        ps.close();

        return rowsCount;
    }

    private String createQueryConditionSql(RecordQueryCondition condition, Ref<Object[]> outParams) {
        if (condition == null) {
            outParams.set(ArrayUtils.EMPTY_OBJECT_ARRAY);
            return StringUtils.EMPTY;
        }

        // 输出参数列表
        List<Object> paramList = new ArrayList<>();
        // 条件连接器
        StringJoiner conditionJoiner = new StringJoiner(" and ");

        // 处理数据类型条件
        String[][] dataFormats = condition.getDataFormats();
        if (!ArrayUtils.isEmpty(dataFormats)) {
            StringJoiner dataFormatsJoiner = new StringJoiner(" or ");

            for (String[] dataFormatString : dataFormats) {
                DataFormat dataFormat =
                        DataFormatUtil.fromJSON(JsonUtil.stringify(dataFormatString));
                if (dataFormat != null) {
                    dataFormatsJoiner.add(StringUtil.interpolate(
                            "${COLUMN_DATA_FORMAT} = ?", 
                            baseValueMap));
                    paramList.add(DataFormatUtil.toJSON(dataFormat));
                }
            }

            String dataFormatsConditionSql = dataFormatsJoiner.toString();
            if (!dataFormatsConditionSql.isEmpty()) {
                conditionJoiner.add(StringUtil.interpolate(
                        "(${dataFormatsConditionSql})", new Object[][] 
                        {
                            {"dataFormatsConditionSql", dataFormatsConditionSql}
                        }));
            }
        }

        // 处理查询内容条件
        String queryContent = condition.getQueryContent();
        if (queryContent != null) {
            char escape = '!';
            queryContent = StringUtil.escapeSqlLike(queryContent, escape);

            Map<String, Object> valueMap = new HashMap<>(baseValueMap);
            valueMap.put("escape", escape);

            conditionJoiner.add(StringUtil.interpolate(
                    "(${COLUMN_CONTENT} like ? escape '${escape}')", 
                    valueMap));
            paramList.add(StringUtil.interpolate(
                    "%${queryContent}%", new Object[][] 
                    {
                        {"queryContent", queryContent}
                    }));
        }

        // 处理开始时间条件
        LocalDateTime startTime = condition.getStartTime();
        if (startTime != null) {
            conditionJoiner.add(StringUtil.interpolate(
                    "(${COLUMN_CREATED_TIME} >= ?)", 
                    baseValueMap));
            paramList.add(LocalDateTimeUtil.toTimeStamp(startTime));
        }

        // 处理结束时间条件
        LocalDateTime endTime = condition.getEndTime();
        if (endTime != null) {
            conditionJoiner.add(StringUtil.interpolate(
                    "(${COLUMN_CREATED_TIME} <= ?)", 
                    baseValueMap));
            paramList.add(LocalDateTimeUtil.toTimeStamp(endTime));
        }

        // 处理是否固定条件
        Boolean isPinned = condition.getIsPinned();
        if (isPinned != null) {
            conditionJoiner.add(StringUtil.interpolate(
                    "(${COLUMN_ISPINNED} = ?)", 
                    baseValueMap));
            paramList.add(isPinned);
        }

        // 生成基本条件Sql语句
        String conditionSql = conditionJoiner.toString();
        if (!conditionSql.isEmpty()) {
            conditionSql = StringUtil.interpolate(
                    "where (${conditionSql})", new Object[][] 
                    {
                        {"conditionSql", conditionSql}
                    });
        }

        // 处理分页条件
        Integer pageSize = condition.getPageSize();
        Integer pageIndex = condition.getPageIndex();
        if (pageSize != null && pageIndex != null) {
            Integer newPageSize = Math.max(pageSize, 1);
            Integer newPageIndex = Math.max(pageIndex, 1);
            conditionSql = StringUtil.interpolate(
                    "${conditionSql} limit ? offest ?", new Object[][] 
                    {
                        {"conditionSql", conditionSql}
                    });
            paramList.add(newPageSize);
            paramList.add(newPageIndex - 1);
        }

        Map<String, Object> valueMap = new HashMap<>(baseValueMap);
        valueMap.put("conditionSql", conditionSql);

        // 添加按创建时间排序条件
        outParams.set(paramList.toArray());
        return StringUtil.interpolate(
                "${conditionSql} order by ${COLUMN_CREATED_TIME} desc",
                valueMap);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createIfNotExists() throws SQLException {
        String baseSql = 
        "create table if not exists ${TABLE_RECORDS} " +
        "(" +
            "${COLUMN_ID} text primary key not null, " +
            "${COLUMN_DATA_FORMAT} text not null, " +
            "${COLUMN_CREATED_TIME} timestamp, " +
            "${COLUMN_MODIFIED_TIME} timestamp, " +
            "${COLUMN_CONTENT} text, " +
            "${COLUMN_ISPINNED} boolean not null" +
        ")";

        String sql = StringUtil.interpolate(baseSql, baseValueMap);
        manipulateRecords(sql, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(Record record) throws SQLException {
        String baseSql =
        "insert into ${TABLE_RECORDS} " +
        "(" +
            "${COLUMN_ID}, " +
            "${COLUMN_DATA_FORMAT}, " +
            "${COLUMN_CREATED_TIME}, " +
            "${COLUMN_MODIFIED_TIME}, " +
            "${COLUMN_CONTENT}, " +
            "${COLUMN_ISPINNED}" +
        ") " +
        "values (?, ?, ?, ?, ?, ?)";

        String sql = StringUtil.interpolate(baseSql, baseValueMap);
        manipulateRecords(sql, (ps) -> {
            ps.setString(1, record.getID());
            ps.setString(2, DataFormatUtil.toJSON(record.getDataFormat()));
            ps.setTimestamp(3, LocalDateTimeUtil.toTimeStamp(record.getCreatedTime()));
            ps.setTimestamp(4, LocalDateTimeUtil.toTimeStamp(record.getModifiedTime()));
            ps.setString(5, record.getContent());
            ps.setBoolean(6, record.getIsPinned());
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(Record record) throws SQLException {
        String baseSql =
        "update ${TABLE_RECORDS} set " +
            "${COLUMN_DATA_FORMAT} = ?, " +
            "${COLUMN_CREATED_TIME} = ?, " +
            "${COLUMN_MODIFIED_TIME} = ?, " +
            "${COLUMN_CONTENT} = ?, " +
            "${COLUMN_ISPINNED} = ? " +
        "where ${COLUMN_ID} = ?";

        String sql = StringUtil.interpolate(baseSql, baseValueMap);
        manipulateRecords(sql, (ps) -> {
            ps.setString(1, DataFormatUtil.toJSON(record.getDataFormat()));
            ps.setTimestamp(2, LocalDateTimeUtil.toTimeStamp(record.getCreatedTime()));
            ps.setTimestamp(3, LocalDateTimeUtil.toTimeStamp(record.getModifiedTime()));
            ps.setString(4, record.getContent());
            ps.setBoolean(5, record.getIsPinned());
            ps.setString(6, record.getID());
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(String id) throws SQLException {
        String baseSql = 
        "delete from ${TABLE_RECORDS} " +
        "where ${COLUMN_ID} = ?";

        String sql = StringUtil.interpolate(baseSql, baseValueMap);
        manipulateRecords(sql, (ps) -> {
            ps.setString(1, id);
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteBatch(String... ids) throws SQLException {
        List<String> idList = new ArrayList<>(Arrays.asList(ids != null ? ids : ArrayUtils.EMPTY_STRING_ARRAY));
        idList.removeAll(Collections.singleton(null));
        if (idList.isEmpty()) {
            return;
        }

        String baseSql = 
        "delete from ${TABLE_RECORDS} " +
        "where ${COLUMN_ID} in (${idHolders})";

        StringJoiner placeHolderJoiner = new StringJoiner(",");
        int count = idList.size();
        for (int i = 0; i < count; i++) {
            placeHolderJoiner.add("?");
        }
        String idHolders = placeHolderJoiner.toString();

        Map<String, Object> valueMap = new HashMap<>(baseValueMap);
        valueMap.put("idHolders", idHolders);

        String sql = StringUtil.interpolate(baseSql, valueMap);
        manipulateRecords(sql, (ps) -> {
            int i = 1;
            for (String id : idList) {
                ps.setString(i++, id);
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteAll() throws SQLException {
        String baseSql = "delete from ${TABLE_RECORDS}";
        String sql = StringUtil.interpolate(baseSql, baseValueMap);
        manipulateRecords(sql, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Record getById(String id) throws SQLException {
        if (id == null) {
            return null;
        }

        String baseSql = 
        "select * from ${TABLE_RECORDS} " +
        "where ${COLUMN_ID} = ?";

        String sql = StringUtil.interpolate(baseSql, baseValueMap);
        List<Record> recordList = queryRecords(sql, (ps) -> {
            ps.setString(1, id);
        });
        return !recordList.isEmpty() ? recordList.get(0) : null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Record> getByIdList(String... ids) throws SQLException {
        List<String> idList =
                new ArrayList<>(Arrays.asList(ids != null ? ids : ArrayUtils.EMPTY_STRING_ARRAY));
        idList.removeAll(Collections.singleton(null));
        if (idList.isEmpty()) {
            List<Record> recordList = new ArrayList<>();
            return Collections.unmodifiableList(recordList);
        }

        String baseSql = 
        "select * from ${TABLE_RECORDS} " +
        "where ${COLUMN_ID} in (${idHolders})";
        
        StringJoiner placeHolderJoiner = new StringJoiner(",");
        int count = idList.size();
        for (int i = 0; i < count; i++) {
            placeHolderJoiner.add("?");
        }
        String idHolders = placeHolderJoiner.toString();

        Map<String, Object> valueMap = new HashMap<>(baseValueMap);
        valueMap.put("idHolders", idHolders);

        String sql = StringUtil.interpolate(baseSql, valueMap);
        return queryRecords(sql, (ps) -> {
            int i = 1;
            for (String id : idList) {
                ps.setString(i++, id);
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Record> getByCondition(RecordQueryCondition condition) throws SQLException {
        String baseSql = "select * from ${TABLE_RECORDS} ${conditionSql}";
        Ref<Object[]> outParams = new Ref<>();
        String conditionSql = createQueryConditionSql(condition, outParams);

        Map<String, Object> valueMap = new HashMap<>(baseValueMap);
        valueMap.put("conditionSql", conditionSql);

        String sql = StringUtil.interpolate(baseSql, valueMap);
        return queryRecords(sql, (ps) -> {
            int i = 1;
            for (Object param : outParams.get()) {
                ps.setObject(i++, param);
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCountByCondition(RecordQueryCondition condition) throws SQLException {
        String baseSql = "select count(*) from ${TABLE_RECORDS} ${conditionSql}";
        Ref<Object[]> outParams = new Ref<>();
        String conditionSql = createQueryConditionSql(condition, outParams);

        Map<String, Object> valueMap = new HashMap<>(baseValueMap);
        valueMap.put("conditionSql", conditionSql);

        String sql = StringUtil.interpolate(baseSql, valueMap);
        return queryRecordsCount(sql, (ps) -> {
            int i = 1;
            for (Object param : outParams.get()) {
                ps.setObject(i++, param);
            }
        });
    }
}
