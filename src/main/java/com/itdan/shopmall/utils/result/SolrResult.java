package com.itdan.shopmall.utils.result;

import java.io.Serializable;

/**
 * solr查询对象
 */
public class SolrResult implements Serializable {

    private  String id;
    private  String title;//标题
    private  String sell_point;//卖点
    private  long price;
    private  String image;
    private  String categroy_name;//商品类型

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSell_point() {
        return sell_point;
    }

    public void setSell_point(String sell_point) {
        this.sell_point = sell_point;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategroy_name() {
        return categroy_name;
    }

    public void setCategroy_name(String categroy_name) {
        this.categroy_name = categroy_name;
    }

    public String [] getImages(){
        if(image!=null&&"".equals(image)){
            String[]strings=image.split(",");
         return strings;
        }
        return null;
    }
}
