package training360.bitsnbytes.rubberduck.product;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class ProductAddressGeneratorTest {

    @Test
    public  void testGenerateAddress() {

        ProductAddressGenerator productAddressGenerator = new ProductAddressGenerator();

        String generatedAddress = productAddressGenerator.generateAddress(new Product(
                1111L, "genadd", "GEN duck", null, "Quack", 1000, ProductStatus.ACTIVE));
        String generatedAddress2 = productAddressGenerator.generateAddress(new Product(
                1111L, "genadd", "GÉN dück", null, "Quack", 1000, ProductStatus.ACTIVE));

        assertEquals("gen-duck-1111", generatedAddress);
        assertEquals("gen-duck-1111", generatedAddress2);


    }

}
