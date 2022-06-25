package com.changgou.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.changgou.content.feign.ContentFeign;
import com.changgou.content.pojo.Content;
import com.changgou.entity.Result;
import com.xpand.starter.canal.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

/**
 * 监控数据库的数据
 */
@CanalEventListener
public class CanalDataEnventListener {

    @Autowired(required = false)
    private ContentFeign contentFeign;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    // 自定义监听器
    @ListenPoint(destination = "example", schema = "changgou_content", table = {"tb_content"},
            eventType = {CanalEntry.EventType.INSERT, CanalEntry.EventType.UPDATE})
    public void onEventContent(CanalEntry.EntryType entryType, CanalEntry.RowData rowData){
        // 获取分类id
        String categoryId = getColumnValue(rowData, "category_id");
        // 通过分类id查询广告列表
        Result<List<Content>> result = contentFeign.findByCategory(Long.parseLong(categoryId));
        // 存入redis
        stringRedisTemplate.boundValueOps("content_" + categoryId).set(JSON.toJSONString(result.getData()));
    }

    // 获取对应列的值
    private String getColumnValue(CanalEntry.RowData rowData, String columnName) {
        List<CanalEntry.Column> list = rowData.getAfterColumnsList();
        for (CanalEntry.Column column : list) {
            if (columnName.equals(column.getName())){
                return column.getValue();
            }
        }
        return null;
    }

    /**
     * @author 栗子
     * @Description
     * @Date 12:29 2019/10/20
     * @param entryType 监控对数据库操作的事件
     * @param rowData   操作的行数据
     * @return void
     **/
    @InsertListenPoint
    public void onEnventInsert(CanalEntry.EntryType entryType, CanalEntry.RowData rowData){
        List<CanalEntry.Column> afterColumnsList = rowData.getAfterColumnsList();
        for (CanalEntry.Column column : afterColumnsList) {
            String columnName = column.getName();   // 列名称
            String value = column.getValue();       // 列对应的值
            System.out.println("列名：" + columnName + "列值：" + value);
        }
    }

    /**
     * @author 栗子
     * @Description
     * @Date 12:29 2019/10/20
     * @param entryType 监控对数据库操作的事件
     * @param rowData   操作的行数据
     * @return void
     **/
    @UpdateListenPoint
    public void onEnventUpdate(CanalEntry.EntryType entryType, CanalEntry.RowData rowData){
        List<CanalEntry.Column> beforeColumnsList = rowData.getBeforeColumnsList(); // 更新前的数据
        for (CanalEntry.Column column : beforeColumnsList) {
            String columnName = column.getName();   // 列名称
            String value = column.getValue();       // 列对应的值
            System.out.println("列名：" + columnName + "列值：" + value);
        }
        System.out.println("------------更新前后的数据--------------");
        List<CanalEntry.Column> afterColumnsList = rowData.getAfterColumnsList();   // 更新后的数据
        for (CanalEntry.Column column : afterColumnsList) {
            String columnName = column.getName();   // 列名称
            String value = column.getValue();       // 列对应的值
            System.out.println("列名：" + columnName + "列值：" + value);
        }
    }

    /**
     * @author 栗子
     * @Description
     * @Date 12:29 2019/10/20
     * @param entryType 监控对数据库操作的事件
     * @param rowData   操作的行数据
     * @return void
     **/
    @DeleteListenPoint
    public void onEnventDelete(CanalEntry.EntryType entryType, CanalEntry.RowData rowData){
        List<CanalEntry.Column> beforeColumnsList = rowData.getBeforeColumnsList(); // 更新前的数据
        for (CanalEntry.Column column : beforeColumnsList) {
            String columnName = column.getName();   // 列名称
            String value = column.getValue();       // 列对应的值
            System.out.println("列名：" + columnName + "列值：" + value);
        }
    }
}
