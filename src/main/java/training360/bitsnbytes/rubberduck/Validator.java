package training360.bitsnbytes.rubberduck;

import training360.bitsnbytes.rubberduck.product.Product;

public class Validator {

    public boolean allFieldsPopulated(Product product) {
        return !(isEmpty(product.getName()) ||
                isEmpty(product.getCode()) ||
                isEmpty(product.getManufacturer()) ||
                isEmpty(String.valueOf(product.getPrice())));
    }

    public boolean allFieldsPopulatedUpdate(Product product) {
        return !(isEmpty(product.getName()) ||
                isEmpty(product.getCode()) ||
                isEmpty(product.getAddress()) ||
                isEmpty(product.getManufacturer()) ||
                isEmpty(String.valueOf(product.getPrice())));
    }


    public boolean isPriceInRange(int price) {
        return price <= 2_000_000 && price > 0;
    }

    public boolean isQuantityPositive (int quantity) {
        return 0 < quantity;
    }

    private boolean isEmpty(String string) {
        return string == null || string.trim().equals("");
    }


    public ResponseStatus passwordValidation(String password) {
        if (password.length() > 15 || password.length() < 8) {
            return new ResponseStatus(false, "A jelszónak 8-15 karakter hosszúságúnak kell lennie!");
        }
        String upperCaseChars = "(.*[A-Z].*)";
        if (!password.matches(upperCaseChars)) {
            return new ResponseStatus(false, "A jelszónak tartalmaznia kell legalább egy nagybetűs karaktert!");
        }
        String lowerCaseChars = "(.*[a-z].*)";
        if (!password.matches(lowerCaseChars)) {
            return new ResponseStatus(false, "A jelszónak tartalmaznia kell legalább egy kisbetűs karaktert!");
        }
        String numbers = "(.*[0-9].*)";
        if (!password.matches(numbers)) {
            return new ResponseStatus(false, "A jelszónak tartalmaznia kell legalább egy számot!");
        }
        return new ResponseStatus(true, "Sikeres regisztráció!");
    }

    public ResponseStatus isEmptyPasswordAndName(String password, String name) {
        if (password.trim().length() == 0) {
            return new ResponseStatus(false, "A jelszó mező kitöltése kötelező!");
        } else if(name.trim().length() == 0){
            return new ResponseStatus(false, "A név mező kitöltése kötelező!");
        }
        return new ResponseStatus(true, "Sikeres adatmódosítás");
    }

    public ResponseStatus addressValidation(String address){
        if(address == null || "".equals(address)){
            return new ResponseStatus(false, "Válassz a meglévő szállítási címekből, vagy adj meg új címet!");
        }
        return new ResponseStatus(true, "Sikeres rendeslés");
    }

}
