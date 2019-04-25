package training360.bitsnbytes.rubberduck.product;

import java.text.Normalizer;

public class ProductAddressGenerator {

    public String generateAddress(Product product) {
        String address;
        address = product.getName().trim().replaceAll(" ", "-").replaceAll(":", "").concat("-").concat(String.valueOf(product.getId())).toLowerCase();
       return Normalizer.normalize(address, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");

    }
}
