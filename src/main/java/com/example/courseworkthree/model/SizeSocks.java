package com.example.courseworkthree.model;




public enum SizeSocks {

    XS(40),
    S(42),
    M(44),
    L(46),
    XL(48);
    private final Integer sizeNum;

    SizeSocks(Integer sizeNum) {
        this.sizeNum = sizeNum;
    }

    public Integer getSizeNum() {
        return sizeNum;
    }
}
