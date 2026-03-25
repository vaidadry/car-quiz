package com.dryzaite.carquiz.shared.model

object BrandCatalog {
    val allBrands: List<CarBrand> = listOf(
        CarBrand("audi", "Audi", BrandRegion.EU, "AU", 0xFFB0BEC5),
        CarBrand("bmw", "BMW", BrandRegion.EU, "BM", 0xFF1565C0),
        CarBrand("mercedes", "Mercedes-Benz", BrandRegion.EU, "MB", 0xFF546E7A),
        CarBrand("volkswagen", "Volkswagen", BrandRegion.EU, "VW", 0xFF0D47A1),
        CarBrand("porsche", "Porsche", BrandRegion.EU, "PO", 0xFF6D4C41),
        CarBrand("volvo", "Volvo", BrandRegion.EU, "VO", 0xFF607D8B),
        CarBrand("renault", "Renault", BrandRegion.EU, "RE", 0xFFF9A825),
        CarBrand("peugeot", "Peugeot", BrandRegion.EU, "PE", 0xFF37474F),
        CarBrand("ford", "Ford", BrandRegion.USA, "FO", 0xFF0D47A1),
        CarBrand("chevrolet", "Chevrolet", BrandRegion.USA, "CH", 0xFFFBC02D),
        CarBrand("tesla", "Tesla", BrandRegion.USA, "TE", 0xFFD32F2F),
        CarBrand("jeep", "Jeep", BrandRegion.USA, "JE", 0xFF33691E),
        CarBrand("cadillac", "Cadillac", BrandRegion.USA, "CA", 0xFF455A64),
        CarBrand("gmc", "GMC", BrandRegion.USA, "GM", 0xFFC62828),
        CarBrand("lincoln", "Lincoln", BrandRegion.USA, "LI", 0xFF37474F),
        CarBrand("toyota", "Toyota", BrandRegion.JAPAN, "TO", 0xFFD32F2F),
        CarBrand("honda", "Honda", BrandRegion.JAPAN, "HO", 0xFFB71C1C),
        CarBrand("nissan", "Nissan", BrandRegion.JAPAN, "NI", 0xFF455A64),
        CarBrand("mazda", "Mazda", BrandRegion.JAPAN, "MA", 0xFF283593),
        CarBrand("subaru", "Subaru", BrandRegion.JAPAN, "SU", 0xFF1E88E5)
    )
}
