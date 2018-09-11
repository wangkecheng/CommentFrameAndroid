package warron.phpprojectandroid.Base;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;
/**
 * onCreated = "sql"：当第一次创建表需要插入数据时候在此写sql语句
 */
@Table(name = "userInfoModel", onCreated = "")
public class UserInfoModel {

    public UserInfoModel(){

    }
    //并不是所有的实体对象都快可以通过这种方式去存储，
    //一定要保证对象的类型中有int类型的id或者_id的属性，
    //这就对应数据库表中的主键字段。如果类型中没有id字段，
    //可以通过@Id注解去指定一个int类型的字段作为主键。
    //如果表中的又字段不想被存储在数据库中，
    //也可以通过@Transient去实现忽略。
    // 如果直接存储一个对象的列表，这样也是被允许的，达到批量存储的目的。

    /**
     * name = "keyId"：数据库表中的一个字段
     * isId = true：是否是主键
     * autoGen = true：是否自动增长  似乎不能用
     * property = "NOT NULL"：添加约束 似乎不能用
     */
    @Column(name = "id",isId = true)
    public long id;//这个作为数据库中数据的唯一标识 这个标识的值 等于  keyId的int值

    @Column(name = "keyId")
    public String keyId;

    @Column(name = "isMember")
    public int isMember;

    @Column(name = "isRecentLogin")
    public int isRecentLogin;//是否是最近登录用户

    @Column(name = "mobile")
    public String mobile;

    @Column(name = "userName")
    public String userName;

    @Column(name = "headImg")
    public String headImg;

    @Column(name = "birthday")
    public String birthday;

    @Column(name = "sex")
    public String sex;//性别

    @Column(name = "address")
    public String address;

    @Column(name = "nickName")
    public String nickName;

    @Column(name = "major")
    public String major;//系或者学院

    @Column(name = "className")
    public String className;

    @Column(name = "partment")
    public String partment;

    @Column(name = "position")
    public String position;

    @Column(name = "university")
    public String university;//大学名

    @Column(name = "password")
    public String password;//大学名
    //默认的构造方法必须写出，如果没有，这张表是创建不成功的
    public long getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
        this.id = Long.parseLong(keyId);//这里做一个强制转换
    }

    public int getIsMember() {
        return isMember;
    }

    public void setIsMember(int isMember) {
        this.isMember = isMember;
    }

    public int getIsRecentLogin() {
        return isRecentLogin;
    }

    public void setIsRecentLogin(int isRecentLogin) {
        this.isRecentLogin = isRecentLogin;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getPartment() {
        return partment;
    }

    public void setPartment(String partment) {
        this.partment = partment;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
