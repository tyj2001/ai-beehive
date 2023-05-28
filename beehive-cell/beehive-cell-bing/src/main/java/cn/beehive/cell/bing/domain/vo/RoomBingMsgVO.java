package cn.beehive.cell.bing.domain.vo;

import cn.beehive.base.enums.MessageTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * @author hncboy
 * @date 2023/5/26
 * Bing 房间消息展示参数
 */
@Data
@Schema(title = "Bing 房间消息展示参数")
public class RoomBingMsgVO {

    @Schema(title = "主键")
    private Long id;

    @Schema(title = "消息内容")
    private String content;

    @Schema(title = "消息类型")
    private MessageTypeEnum type;

    @Schema(title = "创建时间")
    private Date createTime;
}
