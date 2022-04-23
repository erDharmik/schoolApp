package com.aspirepublicschool.gyanmanjari.uniform;

public class student_profileModel {
    private int id;
    private String stu_id;
    private String stu_img,f_img,m_img;
    private String gr_no;
    private String st_sname;
    private String st_fname;
    private String f_nname;
    private String st_cno;
    private String f_cno;
    private String st_email;
    private String cid;
    private String gender;
    private String med;
    private String board;
    private String address;
    private String city;
    private String state;
    private String dob;
    private String nation;
    private String stream;
    private String cast,std,division,sc_name,m_cno,m_name,rollno;


    public student_profileModel(int id, String stu_id, String rollno, String stu_img, String f_img, String m_img, String gr_no, String st_sname, String st_fname, String f_nname, String m_name, String st_cno, String f_cno, String m_cno, String st_email, String cid, String gender, String med, String board, String address, String city, String state, String dob, String nation, String stream, String cast, String std, String division) {
        this.id = id;
        this.stu_id = stu_id;
        this.rollno = rollno;
        this.stu_img = stu_img;
        this.f_img= f_img;
        this.m_img = m_img;
        this.gr_no = gr_no;
        this.st_sname = st_sname;
        this.st_fname = st_fname;
        this.f_nname = f_nname;
        this.m_name = m_name;
        this.st_cno = st_cno;
        this.f_cno = f_cno;
        this.m_cno = m_cno;
        this.st_email = st_email;
        this.cid = cid;
        this.gender = gender;
        this.med = med;
        this.board = board;
        this.address = address;
        this.city = city;
        this.state = state;
        this.dob = dob;
        this.nation = nation;
        this.stream = stream;
        this.cast = cast;
        this.std = std;
        this.division = division;

    }

    public String getF_img() {
        return f_img;
    }

    public void setF_img(String f_img) {
        this.f_img = f_img;
    }

    public String getM_img() {
        return m_img;
    }

    public void setM_img(String m_img) {
        this.m_img = m_img;
    }

    public String getM_name() {
        return m_name;
    }

    public void setM_name(String m_name) {
        this.m_name = m_name;
    }

    public String getM_cno() {
        return m_cno;
    }

    public void setM_cno(String m_cno) {
        this.m_cno = m_cno;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStu_id() {
        return stu_id;
    }

    public void setStu_id(String stu_id) {
        this.stu_id = stu_id;
    }

    public String getStu_img() {
        return stu_img;
    }

    public void setStu_img(String stu_img) {
        this.stu_img = stu_img;
    }

    public String getGr_no() {
        return gr_no;
    }

    public void setGr_no(String gr_no) {
        this.gr_no = gr_no;
    }

    public String getSt_sname() {
        return st_sname;
    }

    public void setSt_sname(String st_sname) {
        this.st_sname = st_sname;
    }

    public String getSt_fname() {
        return st_fname;
    }

    public void setSt_fname(String st_fname) {
        this.st_fname = st_fname;
    }

    public String getF_nname() {
        return f_nname;
    }

    public void setF_nname(String f_nname) {
        this.f_nname = f_nname;
    }

    public String getSt_cno() {
        return st_cno;
    }

    public void setSt_cno(String st_cno) {
        this.st_cno = st_cno;
    }

    public String getF_cno() {
        return f_cno;
    }

    public void setF_cno(String f_cno) {
        this.f_cno = f_cno;
    }

    public String getSt_email() {
        return st_email;
    }

    public void setSt_email(String st_email) {
        this.st_email = st_email;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMed() {
        return med;
    }

    public void setMed(String med) {
        this.med = med;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public String getStd() {
        return std;
    }

    public void setStd(String std) {
        this.std = std;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }


}
