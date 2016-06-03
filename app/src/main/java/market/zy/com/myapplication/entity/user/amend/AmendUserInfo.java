package market.zy.com.myapplication.entity.user.amend;

/**
 * Created by zpauly on 16-5-22.
 */
public class AmendUserInfo {
    /**
     * userId : 17
     * name : haha
     * location : 江西南昌
     * sex : 女
     * contact : 13312312123
     * about_me : 七牛云存储
     * avatar : http://7xrkww.com1.z0.glb.clouddn.com/Frkrfg7cCZ3xO3mwmKXCfbdQ-FQ0
     */

    private int userId;
    private String name;
    private String location;
    private String sex;
    private String contact;
    private String about_me;
    private String avatar;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAbout_me() {
        return about_me;
    }

    public void setAbout_me(String about_me) {
        this.about_me = about_me;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
