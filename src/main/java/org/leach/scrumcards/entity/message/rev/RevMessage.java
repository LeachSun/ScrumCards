package org.leach.scrumcards.entity.message.rev;

/**
 * @author Leach
 * @date 2017/9/9
 */
public class RevMessage {

    private RevMsgType type;

    private String content;

    public RevMsgType getType() {
        return type;
    }

    public void setType(RevMsgType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
