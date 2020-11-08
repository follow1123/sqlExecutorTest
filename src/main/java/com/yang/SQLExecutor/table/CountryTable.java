package com.yang.SQLExecutor.table;

/**
 * @auther YF
 * @create 2020-11-06-20:19
 */
public class CountryTable extends Table {

    public String countryCode = CountryCode;
    public String countryName = CountryName;
    private static final String CountryCode = "CountryCode";
    private static final String CountryName = "CountryName";
    public static CountryTable instance;

    public static CountryTable getInstance() {
        if (instance == null){
            instance = new CountryTable();
        }
        return instance;
    }

    private CountryTable() {
        super("country", CountryCode, CountryName);
    }



}
