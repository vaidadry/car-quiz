package com.dryzaite.carquiz.core.model

import carquiz.composeapp.generated.resources.*
import carquiz.composeapp.generated.resources.ic_acura
import carquiz.composeapp.generated.resources.ic_astonmartin
import carquiz.composeapp.generated.resources.ic_audi
import carquiz.composeapp.generated.resources.ic_bentley
import carquiz.composeapp.generated.resources.ic_bmw
import carquiz.composeapp.generated.resources.ic_bugatti
import carquiz.composeapp.generated.resources.ic_cadillac
import carquiz.composeapp.generated.resources.ic_chevrolet
import carquiz.composeapp.generated.resources.ic_chrysler
import carquiz.composeapp.generated.resources.ic_citroen
import carquiz.composeapp.generated.resources.ic_ferrari
import carquiz.composeapp.generated.resources.ic_fiat
import carquiz.composeapp.generated.resources.ic_ford
import carquiz.composeapp.generated.resources.ic_gmc
import carquiz.composeapp.generated.resources.ic_honda
import carquiz.composeapp.generated.resources.ic_hyundai
import carquiz.composeapp.generated.resources.ic_infiniti
import carquiz.composeapp.generated.resources.ic_jeep
import carquiz.composeapp.generated.resources.ic_lamborghini
import carquiz.composeapp.generated.resources.ic_lincoln
import carquiz.composeapp.generated.resources.ic_maserati
import carquiz.composeapp.generated.resources.ic_mazda
import carquiz.composeapp.generated.resources.ic_mclaren
import carquiz.composeapp.generated.resources.ic_mercedes
import carquiz.composeapp.generated.resources.ic_mini
import carquiz.composeapp.generated.resources.ic_mitsubishi
import carquiz.composeapp.generated.resources.ic_nissan
import carquiz.composeapp.generated.resources.ic_opel
import carquiz.composeapp.generated.resources.ic_peugeot
import carquiz.composeapp.generated.resources.ic_porsche
import carquiz.composeapp.generated.resources.ic_ram
import carquiz.composeapp.generated.resources.ic_renault
import carquiz.composeapp.generated.resources.ic_rollsroyce
import carquiz.composeapp.generated.resources.ic_seat
import carquiz.composeapp.generated.resources.ic_skoda
import carquiz.composeapp.generated.resources.ic_subaru
import carquiz.composeapp.generated.resources.ic_suzuki
import carquiz.composeapp.generated.resources.ic_tesla
import carquiz.composeapp.generated.resources.ic_toyota
import carquiz.composeapp.generated.resources.ic_volkswagen
import carquiz.composeapp.generated.resources.ic_volvo

object BrandCatalog {
    val allBrands: List<CarBrand> = listOf(
        CarBrand("audi", "Audi", BrandRegion.EU, Res.drawable.ic_audi, 0xFFB0BEC5),
        CarBrand("bmw", "BMW", BrandRegion.EU, Res.drawable.ic_bmw, 0xFF1565C0),
        CarBrand("mercedes", "Mercedes-Benz", BrandRegion.EU, Res.drawable.ic_mercedes, 0xFF546E7A),
        CarBrand("volkswagen", "Volkswagen", BrandRegion.EU, Res.drawable.ic_volkswagen, 0xFF0D47A1),
        CarBrand("porsche", "Porsche", BrandRegion.EU, Res.drawable.ic_porsche, 0xFF6D4C41),
        CarBrand("volvo", "Volvo", BrandRegion.EU, Res.drawable.ic_volvo, 0xFF607D8B),
        CarBrand("renault", "Renault", BrandRegion.EU, Res.drawable.ic_renault, 0xFFF9A825),
        CarBrand("peugeot", "Peugeot", BrandRegion.EU, Res.drawable.ic_peugeot, 0xFF37474F),
        CarBrand("ferrari", "Ferrari", BrandRegion.EU, Res.drawable.ic_ferrari, 0xFFC62828),
        CarBrand("lamborghini", "Lamborghini", BrandRegion.EU, Res.drawable.ic_lamborghini, 0xFFF9A825),
        CarBrand("fiat", "Fiat", BrandRegion.EU, Res.drawable.ic_fiat, 0xFF1565C0),
        CarBrand("citroen", "Citroën", BrandRegion.EU, Res.drawable.ic_citroen, 0xFFE53935),
        CarBrand("skoda", "Škoda", BrandRegion.EU, Res.drawable.ic_skoda, 0xFF2E7D32),
        CarBrand("seat", "SEAT", BrandRegion.EU, Res.drawable.ic_seat, 0xFF37474F),
        CarBrand("opel", "Opel", BrandRegion.EU, Res.drawable.ic_opel, 0xFF455A64),
        CarBrand("mini_cooper", "Mini Cooper", BrandRegion.EU, Res.drawable.ic_mini, 0xFF616161),
        CarBrand("bugatti", "Bugatti", BrandRegion.EU, Res.drawable.ic_bugatti, 0xFF0D47A1),
        CarBrand("bentley", "Bentley", BrandRegion.EU, Res.drawable.ic_bentley, 0xFF2E7D32),
        CarBrand("maserati", "Maserati", BrandRegion.EU, Res.drawable.ic_maserati, 0xFF1565C0),
        CarBrand("mclaren", "McLaren", BrandRegion.EU, Res.drawable.ic_mclaren, 0xFFFF6F00),
        CarBrand("astonmartin", "Aston Martin", BrandRegion.EU, Res.drawable.ic_astonmartin, 0xFF004D40),
        CarBrand("rollsroyce", "Rolls-Royce", BrandRegion.EU, Res.drawable.ic_rollsroyce, 0xFF263238),
        CarBrand("ford", "Ford", BrandRegion.USA, Res.drawable.ic_ford, 0xFF0D47A1),
        CarBrand("chevrolet", "Chevrolet", BrandRegion.USA, Res.drawable.ic_chevrolet, 0xFFFBC02D),
        CarBrand("tesla", "Tesla", BrandRegion.USA, Res.drawable.ic_tesla, 0xFFD32F2F),
        CarBrand("jeep", "Jeep", BrandRegion.USA, Res.drawable.ic_jeep, 0xFF33691E),
        CarBrand("cadillac", "Cadillac", BrandRegion.USA, Res.drawable.ic_cadillac, 0xFF455A64),
        CarBrand("gmc", "GMC", BrandRegion.USA, Res.drawable.ic_gmc, 0xFFC62828),
        CarBrand("lincoln", "Lincoln", BrandRegion.USA, Res.drawable.ic_lincoln, 0xFF37474F),
        CarBrand("chrysler", "Chrysler", BrandRegion.USA, Res.drawable.ic_chrysler, 0xFF546E7A),
        CarBrand("ram", "Ram", BrandRegion.USA, Res.drawable.ic_ram, 0xFF37474F),
        CarBrand("toyota", "Toyota", BrandRegion.JAPAN, Res.drawable.ic_toyota, 0xFFD32F2F),
        CarBrand("honda", "Honda", BrandRegion.JAPAN, Res.drawable.ic_honda, 0xFFB71C1C),
        CarBrand("hyundai", "Hyundai", BrandRegion.JAPAN, Res.drawable.ic_hyundai, 0xFF0D47A1),
        CarBrand("nissan", "Nissan", BrandRegion.JAPAN, Res.drawable.ic_nissan, 0xFF455A64),
        CarBrand("mazda", "Mazda", BrandRegion.JAPAN, Res.drawable.ic_mazda, 0xFF283593),
        CarBrand("subaru", "Subaru", BrandRegion.JAPAN, Res.drawable.ic_subaru, 0xFF1E88E5),
        CarBrand("infiniti", "Infiniti", BrandRegion.JAPAN, Res.drawable.ic_infiniti, 0xFF546E7A),
        CarBrand("mitsubishi", "Mitsubishi", BrandRegion.JAPAN, Res.drawable.ic_mitsubishi, 0xFFD32F2F),
        CarBrand("suzuki", "Suzuki", BrandRegion.JAPAN, Res.drawable.ic_suzuki, 0xFF1976D2),
        CarBrand("acura", "Acura", BrandRegion.JAPAN, Res.drawable.ic_acura, 0xFF455A64),
    )
}
