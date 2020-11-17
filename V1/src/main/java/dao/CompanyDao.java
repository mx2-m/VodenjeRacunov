package dao;

import dao.DaoCrud;
import model.Company;


public interface CompanyDao<O, D> extends DaoCrud<Company, O, D> {
    Company getByName(String name);

    Company getByTaxNumber(int taxNumbe);

    Company getByTaxRegistrationNumber(String registrationNumber);
}