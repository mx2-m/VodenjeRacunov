package dao.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import dao.CompanyDao;
import model.Company;
import util.Util;

import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;

public class JsonCompany implements CompanyDao<List<Company>, JsonReader> {
    private final String filename = "src/main/resources/company.json";


    @Override
    public Company getByName(String name) {

        List<Company> companyList = getAll();
        for (Company company : companyList) {
            if (company.getName().equals(name)) {
                return company;
            }
        }
        return null;
    }

    @Override
    public Company getByTaxNumber(int taxNumber) {
        List<Company> companyList = getAll();
        for (Company company : companyList ) {
            if (company .getTaxNumber() == taxNumber) {
                return company ;
            }
        }
        return null;
    }

    @Override
    public Company getByTaxRegistrationNumber(String registrationNumber) {
        List<Company> companyList= getAll();
        for (Company company : companyList) {
            if (company.getRegistrationNumber().equals(registrationNumber)) {
                return company;
            }
        }
        return null;
    }


    @Override
    public Company getById(UUID id) {

        return null;
    }

    @Override
    public List<Company> getAll() {
        JsonReader reader = Util.readJsonFromFile(filename);
        return mapDataToObject(reader);
    }

    @Override
    public boolean insert(Company m) {
        List<Company> companyList = getAll();
        for (Company company : companyList) {
            if (company.getRegistrationNumber().equals(m.getRegistrationNumber())) {
                return false;
            }
        }
        companyList.add(m);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return Util.writeJsonToFile(filename, gson.toJson(companyList));

    }

    @Override
    public boolean update(Company m) {
        List<Company> companyList = getAll();
        for (Company company : companyList ) {
            if (company.getRegistrationNumber().equals(m.getRegistrationNumber())) {
                companyList.remove(company);
                companyList.add(m);
            }

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return Util.writeJsonToFile(filename, gson.toJson(companyList ));
        }
        return false;
    }

    @Override
    public boolean delete(Company m) {
        List<Company> companyList = getAll();
        for (Company company : companyList) {
            if (company.getRegistrationNumber().equals(m.getRegistrationNumber())) {
                companyList.remove(company);
            }

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return Util.writeJsonToFile(filename, gson.toJson(companyList));
        }
        return false;
    }


    @Override
    public List<Company> mapDataToObject(JsonReader data) {
        Type companyListType = new TypeToken<List<Company>>() {
        }.getType();
        Gson gson = new Gson();
        return gson.fromJson(data, companyListType);
    }


}
