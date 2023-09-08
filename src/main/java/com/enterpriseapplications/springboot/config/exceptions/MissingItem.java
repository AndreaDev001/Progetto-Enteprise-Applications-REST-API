package com.enterpriseapplications.springboot.config.exceptions;

public class MissingItem extends RuntimeException {
    public MissingItem(String name) {super(name);}
}
