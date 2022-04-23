package com.aspirepublicschool.gyanmanjari;

class Advertisement {
    String id;
    int img;

    public Advertisement(String id, int img) {
        this.id = id;
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
