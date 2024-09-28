package com.devmobile.android.restaurant.model.repository.datasource.local

import android.content.Context
import com.devmobile.android.restaurant.R
import com.devmobile.android.restaurant.model.datasource.local.RestaurantLocalDatabase
import com.devmobile.android.restaurant.model.datasource.local.entities.Food
import com.devmobile.android.restaurant.usecase.enums.FoodSection
import com.devmobile.android.restaurant.usecase.enums.TempoPreparo

// Temporary Class
class DatabaseSimulator {

    companion object {

        fun addFoodDataToDatabase(context: Context) {
            val foods = ArrayList<Food>()
            val foodDao = RestaurantLocalDatabase.getInstance(context).getFoodDao()

            if (foodDao.getQuantityOfFoods() == 0) {

                foods.addAll(
                    listOf(
                        Food(
                            1,
                            "Hamburger",
                            25F,
                            FoodSection.ENTRADA,
                            R.drawable.image_entrada_hamburguer,
                            R.drawable.ic_time_prepare_normal,
                            TempoPreparo.NORMAL,
                            "Um suculento hambúrguer grelhado, coberto com queijo derretido, alface crocante, tomate maduro e cebola fresca, tudo envolto em um pão macio de gergelim. Um clássico irresistível para os amantes de hambúrguer."
                        ), Food(
                            2,
                            "Camarão",
                            40F,
                            FoodSection.ENTRADA,
                            R.drawable.image_entrada_camarao,
                            R.drawable.ic_time_prepare_normal,
                            TempoPreparo.NORMAL,
                            "Deliciosos camarões frescos, salteados em um molho de manteiga com alho picante e ervas aromáticas, servidos sobre uma cama de arroz branco perfumado. Uma explosão de sabor do mar em cada garfada."
                        ), Food(
                            3,
                            "Queijo",
                            30F,
                            FoodSection.ENTRADA,
                            R.drawable.image_entrada_queijo,
                            R.drawable.ic_time_prepare_rapido,
                            TempoPreparo.RAPIDO,
                            "Queijo artesanal de textura cremosa e sabor rico, perfeito para acompanhar uma tábua de frios, uma salada ou mesmo um simples sanduíche. Uma escolha versátil e deliciosa para qualquer ocasião."
                        ), Food(
                            4,
                            "Sopa",
                            20F,
                            FoodSection.ENTRADA,
                            R.drawable.image_entrada_sopa,
                            R.drawable.ic_time_prepare_normal,
                            TempoPreparo.NORMAL,
                            "Uma sopa reconfortante, preparada com pedaços suculentos de carne, legumes frescos e um caldo rico e aromático. Ideal para aquecer nos dias frios ou para confortar em qualquer momento."
                        ), Food(
                            5,
                            "Feijoada",
                            60F,
                            FoodSection.PRINCIPAL,
                            R.drawable.image_principal_feijoada,
                            R.drawable.ic_time_prepare_lento,
                            TempoPreparo.LENTO,
                            "Prato brasileiro clássico, repleto de sabores robustos e autênticos. Feijão preto cozido lentamente com uma variedade de carnes defumadas, servido com arroz branco, couve refogada, farofa e laranja. Uma verdadeira festa gastronômica."
                        ), Food(
                            6,
                            "Macarronada",
                            40F,
                            FoodSection.PRINCIPAL,
                            R.drawable.image_principal_macarronada,
                            R.drawable.ic_time_prepare_normal,
                            TempoPreparo.NORMAL,
                            "Uma massa al dente coberta com um generoso molho de tomate caseiro, salsichas suculentas e queijo derretido, gratinado até ficar dourado e borbulhante. Uma comfort food que satisfaz todos os paladares."
                        ), Food(
                            7,
                            "Lasanha",
                            55F,
                            FoodSection.PRINCIPAL,
                            R.drawable.image_principal_lasanha,
                            R.drawable.ic_time_prepare_lento,
                            TempoPreparo.LENTO,
                            "Camadas intercaladas de massa de lasanha, molho bolonhesa rico e queijo derretido, assadas até ficarem douradas e borbulhantes. Um clássico italiano que é sempre uma escolha reconfortante e deliciosa."
                        ), Food(
                            8,
                            "Baião de Dois",
                            34F,
                            FoodSection.PRINCIPAL,
                            R.drawable.image_principal_baiao_dois,
                            R.drawable.ic_time_prepare_normal,
                            TempoPreparo.NORMAL,
                            "Um prato típico do Nordeste brasileiro, composto por arroz cozido com feijão verde, linguiça calabresa, bacon, queijo coalho e temperos saborosos. Uma explosão de sabores regionais em cada garfada."
                        ), Food(
                            9,
                            "Peixe",
                            28F,
                            FoodSection.PRINCIPAL,
                            R.drawable.image_principal_peixe,
                            R.drawable.ic_time_prepare_normal,
                            TempoPreparo.NORMAL,
                            "eixe fresco grelhado, marinado em suco de limão e ervas aromáticas, servido com uma salada leve e refrescante de vegetais da estação. Uma opção leve e saudável, perfeita para os amantes de frutos do mar."
                        ), Food(
                            10,
                            "Peru",
                            91F,
                            FoodSection.PRINCIPAL,
                            R.drawable.image_principal_peru,
                            R.drawable.ic_time_prepare_lento,
                            TempoPreparo.LENTO,
                            "Um peru suculento, assado lentamente com uma mistura de ervas frescas e temperos aromáticos, servido com purê de batatas cremoso e molho de cranberry caseiro. Uma refeição tradicional que traz o espírito festivo para qualquer ocasião."
                        ), Food(
                            11,
                            "Cerveja",
                            80F,
                            FoodSection.BEBIDA,
                            R.drawable.image_bebida_cerveja,
                            R.drawable.ic_time_prepare_rapido,
                            TempoPreparo.RAPIDO,
                            "Cerveja gelada e refrescante, com notas sutis de lúpulo e malte, perfeita para acompanhar uma refeição ou desfrutar em um dia quente de verão."
                        ), Food(
                            12,
                            "Milkshake",
                            35F,
                            FoodSection.BEBIDA,
                            R.drawable.image_bebida_milkshake,
                            R.drawable.ic_time_prepare_rapido,
                            TempoPreparo.RAPIDO,
                            "Um milkshake cremoso e indulgente, feito com sorvete de baunilha e leite, e finalizado com uma generosa camada de chantilly e calda de chocolate."
                        ), Food(
                            13,
                            "Café",
                            25F,
                            FoodSection.BEBIDA,
                            R.drawable.image_bebida_cafe,
                            R.drawable.ic_time_prepare_rapido,
                            TempoPreparo.RAPIDO,
                            "Café aromático e encorpado, preparado com grãos recém-torrados e moídos na hora, proporcionando um sabor rico e intenso que desperta os sentidos a cada gole."
                        ), Food(
                            14,
                            "Suco de Manga",
                            27F,
                            FoodSection.BEBIDA,
                            R.drawable.image_bebida_suco_manga,
                            R.drawable.ic_time_prepare_rapido,
                            TempoPreparo.RAPIDO,
                            "Suco naturalmente doce e refrescante, feito com mangas maduras e suculentas, perfeito para saciar a sede e fornecer uma dose extra de vitaminas e antioxidantes."
                        ), Food(
                            15,
                            "Vinho",
                            60F,
                            FoodSection.BEBIDA,
                            R.drawable.image_bebida_vinho,
                            R.drawable.ic_time_prepare_rapido,
                            TempoPreparo.RAPIDO,
                            "Vinho encorpado e elegante, com notas frutadas e taninos macios, ideal para acompanhar uma refeição sofisticada ou apreciar em uma noite especial."
                        ), Food(
                            16,
                            "Açaí",
                            29F,
                            FoodSection.SOBREMESA,
                            R.drawable.image_sobremesa_acai,
                            R.drawable.ic_time_prepare_rapido,
                            TempoPreparo.RAPIDO,
                            "Uma tigela de açaí cremoso e nutritivo, coberto com frutas frescas, granola crocante e um fio de mel, proporcionando uma explosão de sabor e energia revitalizante."
                        ), Food(
                            17,
                            "Bolo de Cenoura",
                            20F,
                            FoodSection.SOBREMESA,
                            R.drawable.image_sobremesa_bolo_cenoura,
                            R.drawable.ic_time_prepare_rapido,
                            TempoPreparo.RAPIDO,
                            "Bolo fofinho e úmido, repleto de cenoura ralada e nozes picadas, coberto com uma generosa camada de glacê de cream cheese, que derrete na boca em cada mordida."
                        ), Food(
                            18,
                            "Brigadeiro",
                            10F,
                            FoodSection.SOBREMESA,
                            R.drawable.image_sobremesa_brigadeiro,
                            R.drawable.ic_time_prepare_rapido,
                            TempoPreparo.RAPIDO,
                            "Pequenas bolinhas de brigadeiro de chocolate, feitas com cacau em pó e leite condensado, cobertas com granulado colorido, uma sobremesa clássica que é simplesmente irresistível."
                        ), Food(
                            19,
                            "Brownie",
                            23F,
                            FoodSection.SOBREMESA,
                            R.drawable.image_sobremesa_brownie,
                            R.drawable.ic_time_prepare_rapido,
                            TempoPreparo.RAPIDO,
                            "O brownie mais denso e indulgente, repleto de pedaços de chocolate meio amargo e nozes crocantes, perfeito para os amantes de chocolate que desejam uma dose extra de indulgência."
                        ), Food(
                            20,
                            "Coalhada",
                            20F,
                            FoodSection.SOBREMESA,
                            R.drawable.image_sobremesa_coalhada,
                            R.drawable.ic_time_prepare_rapido,
                            TempoPreparo.RAPIDO,
                            "Coalhada cremosa e refrescante, feita com iogurte natural e temperada com ervas frescas e azeite de oliva, uma opção leve e saudável para acompanhar pães ou vegetais frescos."
                        ), Food(
                            21,
                            "Torta de Leite",
                            17F,
                            FoodSection.SOBREMESA,
                            R.drawable.image_sobremesa_torta,
                            R.drawable.ic_time_prepare_rapido,
                            TempoPreparo.RAPIDO,
                            "Uma torta de leite macia e reconfortante, com uma base de massa amanteigada e um recheio cremoso de leite condensado, uma sobremesa clássica que evoca memórias de infância e conforto."
                        )
                    )
                )

                foodDao.insertAll(foods)
            }
        }
    }
}