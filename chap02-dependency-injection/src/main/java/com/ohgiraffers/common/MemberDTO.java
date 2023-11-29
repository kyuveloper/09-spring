package com.ohgiraffers.common;


import lombok.Builder;

@Builder
public class MemberDTO {
    private int seq;
    private String name;
    private String phone;
    private String email;
    private Account personalAccount;

    public MemberDTO() {
    }

    public MemberDTO(int seq, String name, String phone, String email, Account personalAccount) {
        this.seq = seq;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.personalAccount = personalAccount;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Account getPersonalAccount() {
        return personalAccount;
    }

    public void setPersonalAccount(Account personalAccount) {
        this.personalAccount = personalAccount;
    }

    @Override
    public String toString() {
        return "MemberDTO{" +
                "seq=" + seq +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", personalAccount=" + personalAccount +
                '}';
    }
}
