package com.aspirepublicschool.gyanmanjari.uniform.clothes;

class Sizespro {
    String Size,pr_code;

    public Sizespro(String size, String pr_code) {
        Size = size;
        this.pr_code = pr_code;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public String getPr_code() {
        return pr_code;
    }

    public void setPr_code(String pr_code) {
        this.pr_code = pr_code;
    }
}
