package com.yang.SQLExecutor.table;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther YF
 * @create 2020-11-02-20:52
 */
public class CityTable extends Table {

    public static CityTable instance;

    public String cityCode = CityCode;

    public String cityName = CityName;

    public String countryCode = CountryCode;


    private static final String CityCode = "CityCode";
    private static final String CityName = "CityName";
    private static final String CountryCode = "CountryCode";

    public static CityTable getInstance() {
        if (instance == null) {
            instance = new CityTable();
        }
        return instance;
    }

    private CityTable() {
        super("city", CityCode, CityName, CountryCode);
    }
}
