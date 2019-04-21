package com.xiandian.openstack.cloud.swiftstorage.bean;

/**
 * 拍照后自动提示的名字的实体类
 *
 */
public class TakePhotoBean {
    /**
     * 照片名称
     */
    private String photoName;
    /**
     * 可能的概率
     */
    private String nameProbability;

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public String getNameProbability() {
        return nameProbability;
    }

    public void setNameProbability(String nameProbability) {
        this.nameProbability = nameProbability;
    }
}
