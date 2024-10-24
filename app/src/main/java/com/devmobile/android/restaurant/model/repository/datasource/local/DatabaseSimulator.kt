package com.devmobile.android.restaurant.model.repository.datasource.local

import android.content.Context
import com.devmobile.android.restaurant.R
import com.devmobile.android.restaurant.model.datasource.local.RestaurantLocalDatabase
import com.devmobile.android.restaurant.model.datasource.local.entities.Food
import com.devmobile.android.restaurant.usecase.Fetch
import com.devmobile.android.restaurant.usecase.entities.Restaurant
import com.devmobile.android.restaurant.usecase.enums.FoodSection
import com.devmobile.android.restaurant.usecase.enums.TempoPreparo

// Temporary Class
class DatabaseSimulator {

    companion object {

        suspend fun addRestaurants(context: Context) {
            val restaurants = ArrayList<Restaurant>()
            val fetches = ArrayList<Fetch>()
            val restaurantDao = RestaurantLocalDatabase.getInstance(context).getRestaurantDao()
            val fetchDao = RestaurantLocalDatabase.getInstance(context).getFetch()

            if (fetchDao.getCacheSize() == 0) {
                fetches.addAll(
                    listOf(
                        Fetch(fetchName = "Restaurant 1"),
                        Fetch(fetchName = "Restaurant 2"),
                        Fetch(fetchName = "Restaurant 3"),
                        Fetch(fetchName = "Restaurant 4")
                    )
                )
                fetchDao.insertAll(fetches)
            }

            if (restaurantDao.getAll().isEmpty()) {

                restaurants.addAll(
                    listOf(
                        Restaurant(
                            id = 0,
                            name = "Casa do Camarao",
                            street = "Rua do camarao, 003",
                            city = "Rio Grande do Sul",
                            postalCode = "89433-439"
                        ),
                        Restaurant(
                            id = 1,
                            name = "Bella Italia",
                            street = "Avenida das Flores, 123",
                            city = "São Paulo",
                            postalCode = "01234-567"
                        ),
                        Restaurant(
                            id = 2,
                            name = "Sushi House",
                            street = "Rua dos Sushis, 456",
                            city = "Rio de Janeiro",
                            postalCode = "02345-678"
                        ),
                        Restaurant(
                            id = 3,
                            name = "Pizzeria Napoli",
                            street = "Rua da Pizza, 789",
                            city = "Belo Horizonte",
                            postalCode = "03456-789"
                        ),
                        Restaurant(
                            id = 4,
                            name = "Churrascaria Gaúcha",
                            street = "Rua do Churrasco, 101",
                            city = "Porto Alegre",
                            postalCode = "04567-890"
                        ),
                        Restaurant(
                            id = 5,
                            name = "Café do Mercado",
                            street = "Rua das Frutas, 202",
                            city = "Curitiba",
                            postalCode = "05678-901"
                        ),
                        Restaurant(
                            id = 6,
                            name = "Restaurante Mar & Terra",
                            street = "Avenida do Litoral, 303",
                            city = "Salvador",
                            postalCode = "06789-012"
                        ),
                        Restaurant(
                            id = 7,
                            name = "Bistrô Parisiense",
                            street = "Rua de Paris, 404",
                            city = "Florianópolis",
                            postalCode = "07890-123"
                        ),
                        Restaurant(
                            id = 8,
                            name = "Cantina do Italiano",
                            street = "Rua Roma, 505",
                            city = "Recife",
                            postalCode = "08901-234"
                        ),
                        Restaurant(
                            id = 9,
                            name = "Taco Loco",
                            street = "Avenida dos Tacos, 606",
                            city = "Brasília",
                            postalCode = "09012-345"
                        ),
                        Restaurant(
                            id = 10,
                            name = "Pub da Esquina",
                            street = "Rua das Cervejas, 707",
                            city = "Goiânia",
                            postalCode = "10123-456"
                        ),
                        Restaurant(
                            id = 11,
                            name = "Cozinha Mineira",
                            street = "Rua das Minas, 808",
                            city = "Uberlândia",
                            postalCode = "11234-567"
                        ),
                        Restaurant(
                            id = 12,
                            name = "Grill do Chef",
                            street = "Rua do Grelhado, 909",
                            city = "Natal",
                            postalCode = "12345-678"
                        ),
                        Restaurant(
                            id = 13,
                            name = "Vegan Garden",
                            street = "Rua Verde, 111",
                            city = "São Luís",
                            postalCode = "23456-789"
                        ),
                        Restaurant(
                            id = 14,
                            name = "Delícias do Nordeste",
                            street = "Avenida do Nordeste, 222",
                            city = "João Pessoa",
                            postalCode = "34567-890"
                        ),
                        Restaurant(
                            id = 15,
                            name = "Sorveteria Gelato",
                            street = "Rua dos Gelados, 333",
                            city = "Maceió",
                            postalCode = "45678-901"
                        ),
                        Restaurant(
                            id = 16,
                            name = "Burguer & Beer",
                            street = "Rua do Hambúrguer, 444",
                            city = "Vitória",
                            postalCode = "56789-012"
                        ),
                        Restaurant(
                            id = 17,
                            name = "Pão de Queijo & Cia",
                            street = "Avenida do Pão, 555",
                            city = "Campo Grande",
                            postalCode = "67890-123"
                        ),
                        Restaurant(
                            id = 18,
                            name = "Café Colonial",
                            street = "Rua dos Cafés, 666",
                            city = "Caxias do Sul",
                            postalCode = "78901-234"
                        ),
                        Restaurant(
                            id = 19,
                            name = "Chá da Tarde",
                            street = "Rua das Infusões, 777",
                            city = "Teresina",
                            postalCode = "89012-345"
                        ),
                        Restaurant(
                            id = 20,
                            name = "Taberna Medieval",
                            street = "Rua dos Cavaleiros, 888",
                            city = "Aracaju",
                            postalCode = "90123-456"
                        ),
                        Restaurant(
                            id = 21,
                            name = "Restaurante Fusion",
                            street = "Avenida das Misturas, 999",
                            city = "Londrina",
                            postalCode = "01234-567"
                        ),
                        Restaurant(
                            id = 22,
                            name = "Cozinha Africana",
                            street = "Rua do Continente, 1010",
                            city = "Sorocaba",
                            postalCode = "12345-678"
                        ),
                        Restaurant(
                            id = 23,
                            name = "Mamma Mia!",
                            street = "Rua da Nonna, 1111",
                            city = "São José dos Campos",
                            postalCode = "23456-789"
                        ),
                        Restaurant(
                            id = 24,
                            name = "Bistrô do Mar",
                            street = "Avenida do Mar, 1212",
                            city = "Santos",
                            postalCode = "34567-890"
                        ),
                        Restaurant(
                            id = 25,
                            name = "Delícias da Roça",
                            street = "Rua da Fazenda, 1313",
                            city = "Ribeirão Preto",
                            postalCode = "45678-901"
                        ),
                        Restaurant(
                            id = 26,
                            name = "Pizza & Pasta",
                            street = "Rua do Molho, 1414",
                            city = "Niterói",
                            postalCode = "56789-012"
                        ),
                        Restaurant(
                            id = 27,
                            name = "Grill & Chill",
                            street = "Avenida da Relaxação, 1515",
                            city = "Brasília",
                            postalCode = "67890-123"
                        ),
                        Restaurant(
                            id = 28,
                            name = "Piknik Café",
                            street = "Rua da Diversão, 1616",
                            city = "Piracicaba",
                            postalCode = "78901-234"
                        ),
                        Restaurant(
                            id = 29,
                            name = "Bistrô Verde",
                            street = "Rua da Sustentabilidade, 1717",
                            city = "Juiz de Fora",
                            postalCode = "89012-345"
                        ),
                        Restaurant(
                            id = 30,
                            name = "Feijoada do Chef",
                            street = "Rua da Feijoada, 1818",
                            city = "Belém",
                            postalCode = "90123-456"
                        ),
                        Restaurant(
                            id = 31,
                            name = "Culinária Mediterrânea",
                            street = "Avenida do Mediterrâneo, 1919",
                            city = "Blumenau",
                            postalCode = "01234-567"
                        ),
                        Restaurant(
                            id = 32,
                            name = "Restaurante 5 Estrelas",
                            street = "Rua do Luxo, 2020",
                            city = "São Carlos",
                            postalCode = "12345-678"
                        ),
                        Restaurant(
                            id = 33,
                            name = "Pizzaria do Bairro",
                            street = "Rua do Bairro, 2121",
                            city = "São Vicente",
                            postalCode = "23456-789"
                        ),
                        Restaurant(
                            id = 34,
                            name = "Churrasquinho de Rua",
                            street = "Rua da Avenida, 2222",
                            city = "São Bernardo do Campo",
                            postalCode = "34567-890"
                        ),
                        Restaurant(
                            id = 35,
                            name = "Café da Manhã",
                            street = "Avenida do Café, 2323",
                            city = "São José",
                            postalCode = "45678-901"
                        ),
                        Restaurant(
                            id = 36,
                            name = "Burguer Fino",
                            street = "Rua do Gourmet, 2424",
                            city = "Osasco",
                            postalCode = "56789-012"
                        ),
                        Restaurant(
                            id = 37,
                            name = "Espaço Vegano",
                            street = "Rua Verde, 2525",
                            city = "Macaé",
                            postalCode = "67890-123"
                        ),
                        Restaurant(
                            id = 38,
                            name = "Café da Tarde",
                            street = "Avenida do Chá, 2626",
                            city = "Araraquara",
                            postalCode = "78901-234"
                        ),
                        Restaurant(
                            id = 39,
                            name = "Comida de Boteco",
                            street = "Rua do Boteco, 2727",
                            city = "São Caetano do Sul",
                            postalCode = "89012-345"
                        ),
                        Restaurant(
                            id = 40,
                            name = "Bistrô Chic",
                            street = "Rua da Elegância, 2828",
                            city = "Niterói",
                            postalCode = "90123-456"
                        ),
                        Restaurant(
                            id = 41,
                            name = "Pasta & Risotto",
                            street = "Rua da Itália, 2929",
                            city = "São Mateus",
                            postalCode = "01234-567"
                        ),
                        Restaurant(
                            id = 42,
                            name = "Bar e Grill",
                            street = "Rua do Bar, 3030",
                            city = "São João de Meriti",
                            postalCode = "12345-678"
                        ),
                        Restaurant(
                            id = 43,
                            name = "Restaurante Familiar",
                            street = "Rua do Lar, 3131",
                            city = "Santo André",
                            postalCode = "23456-789"
                        ),
                        Restaurant(
                            id = 44,
                            name = "Culinária Brasileira",
                            street = "Rua do Brasil, 3232",
                            city = "Camaçari",
                            postalCode = "34567-890"
                        ),
                        Restaurant(
                            id = 45,
                            name = "Restaurante da Vovó",
                            street = "Rua da Memória, 3333",
                            city = "Nova Iguaçu",
                            postalCode = "45678-901"
                        ),
                        Restaurant(
                            id = 46,
                            name = "Pé na Areia",
                            street = "Avenida da Praia, 3434",
                            city = "Ilhéus",
                            postalCode = "56789-012"
                        ),
                        Restaurant(
                            id = 47,
                            name = "Café com Leite",
                            street = "Rua do Café, 3535",
                            city = "Divinópolis",
                            postalCode = "67890-123"
                        ),
                        Restaurant(
                            id = 48,
                            name = "Pasta e Pesto",
                            street = "Rua do Pesto, 3636",
                            city = "São Gabriel",
                            postalCode = "78901-234"
                        ),
                        Restaurant(
                            id = 49,
                            name = "Taco e Tequila",
                            street = "Rua do Tequila, 3737",
                            city = "Petrolina",
                            postalCode = "89012-345"
                        ),
                        Restaurant(
                            id = 50,
                            name = "Restaurante do Lago",
                            street = "Rua do Lago, 3838",
                            city = "Chapecó",
                            postalCode = "90123-456"
                        )
                    )
                )

                restaurantDao.insertAll(restaurants)
            }
        }

        fun addFoodDataToDatabase(context: Context) {
            val foods = ArrayList<Food>()
            val foodDao = RestaurantLocalDatabase.getInstance(context).getFoodDao()

            if (foodDao.getQuantityOfFoods() == 0) {

                foods.addAll(
                    listOf(
                        Food(
                            id = 1,
                            restaurantId = 0L,
                            name = "Hamburger",
                            price = 25F,
                            section = FoodSection.ENTRADA,
                            imageId = R.drawable.image_entrada_hamburguer,
                            timeForPrepare = TempoPreparo.NORMAL,
                            description = "Um suculento hambúrguer grelhado, coberto com queijo derretido, alface crocante, tomate maduro e cebola fresca, tudo envolto em um pão macio de gergelim. Um clássico irresistível para os amantes de hambúrguer."
                        ), Food(
                            id = 2,
                            restaurantId = 0L,
                            name = "Camarão",
                            price = 40F,
                            section = FoodSection.ENTRADA,
                            imageId = R.drawable.image_entrada_camarao,
                            timeForPrepare = TempoPreparo.NORMAL,
                            description = "Deliciosos camarões frescos, salteados em um molho de manteiga com alho picante e ervas aromáticas, servidos sobre uma cama de arroz branco perfumado. Uma explosão de sabor do mar em cada garfada."
                        ), Food(
                            id = 3,
                            restaurantId = 0L,
                            name = "Queijo",
                            price = 30F,
                            section = FoodSection.ENTRADA,
                            imageId = R.drawable.image_entrada_queijo,
                            timeForPrepare = TempoPreparo.RAPIDO,
                            description = "Queijo artesanal de textura cremosa e sabor rico, perfeito para acompanhar uma tábua de frios, uma salada ou mesmo um simples sanduíche. Uma escolha versátil e deliciosa para qualquer ocasião."
                        ), Food(
                            id = 4,
                            restaurantId = 0L,
                            name = "Sopa",
                            price = 20F,
                            section = FoodSection.ENTRADA,
                            imageId = R.drawable.image_entrada_sopa,
                            timeForPrepare = TempoPreparo.NORMAL,
                            description = "Uma sopa reconfortante, preparada com pedaços suculentos de carne, legumes frescos e um caldo rico e aromático. Ideal para aquecer nos dias frios ou para confortar em qualquer momento."
                        ), Food(
                            id = 5,
                            restaurantId = 0L,
                            name = "Feijoada",
                            price = 60F,
                            section = FoodSection.PRINCIPAL,
                            imageId = R.drawable.image_principal_feijoada,
                            timeForPrepare = TempoPreparo.LENTO,
                            description = "Prato brasileiro clássico, repleto de sabores robustos e autênticos. Feijão preto cozido lentamente com uma variedade de carnes defumadas, servido com arroz branco, couve refogada, farofa e laranja. Uma verdadeira festa gastronômica."
                        ), Food(
                            id = 6,
                            restaurantId = 0L,
                            name = "Macarronada",
                            price = 40F,
                            section = FoodSection.PRINCIPAL,
                            imageId = R.drawable.image_principal_macarronada,
                            timeForPrepare = TempoPreparo.NORMAL,
                            description = "Uma massa al dente coberta com um generoso molho de tomate caseiro, salsichas suculentas e queijo derretido, gratinado até ficar dourado e borbulhante. Uma comfort food que satisfaz todos os paladares."
                        ), Food(
                            id = 7,
                            restaurantId = 0L,
                            name = "Lasanha",
                            price = 55F,
                            section = FoodSection.PRINCIPAL,
                            imageId = R.drawable.image_principal_lasanha,
                            timeForPrepare = TempoPreparo.LENTO,
                            description = "Camadas intercaladas de massa de lasanha, molho bolonhesa rico e queijo derretido, assadas até ficarem douradas e borbulhantes. Um clássico italiano que é sempre uma escolha reconfortante e deliciosa."
                        ), Food(
                            id = 8,
                            restaurantId = 0L,
                            name = "Baião de Dois",
                            price = 34F,
                            section = FoodSection.PRINCIPAL,
                            imageId = R.drawable.image_principal_baiao_dois,
                            timeForPrepare = TempoPreparo.NORMAL,
                            description = "Um prato típico do Nordeste brasileiro, composto por arroz cozido com feijão verde, linguiça calabresa, bacon, queijo coalho e temperos saborosos. Uma explosão de sabores regionais em cada garfada."
                        ), Food(
                            id = 9,
                            restaurantId = 0L,
                            name = "Peixe",
                            price = 28F,
                            section = FoodSection.PRINCIPAL,
                            imageId = R.drawable.image_principal_peixe,
                            timeForPrepare = TempoPreparo.NORMAL,
                            description = "eixe fresco grelhado, marinado em suco de limão e ervas aromáticas, servido com uma salada leve e refrescante de vegetais da estação. Uma opção leve e saudável, perfeita para os amantes de frutos do mar."
                        ), Food(
                            id = 10,
                            restaurantId = 0L,
                            name = "Peru",
                            price = 91F,
                            section = FoodSection.PRINCIPAL,
                            imageId = R.drawable.image_principal_peru,
                            timeForPrepare = TempoPreparo.LENTO,
                            description = "Um peru suculento, assado lentamente com uma mistura de ervas frescas e temperos aromáticos, servido com purê de batatas cremoso e molho de cranberry caseiro. Uma refeição tradicional que traz o espírito festivo para qualquer ocasião."
                        ), Food(
                            id = 11,
                            restaurantId = 0L,
                            name = "Cerveja",
                            price = 80F,
                            section = FoodSection.BEBIDA,
                            imageId = R.drawable.image_bebida_cerveja,
                            timeForPrepare = TempoPreparo.RAPIDO,
                            description = "Cerveja gelada e refrescante, com notas sutis de lúpulo e malte, perfeita para acompanhar uma refeição ou desfrutar em um dia quente de verão."
                        ), Food(
                            id = 12,
                            restaurantId = 0L,
                            name = "Milkshake",
                            price = 35F,
                            section = FoodSection.BEBIDA,
                            imageId = R.drawable.image_bebida_milkshake,
                            timeForPrepare = TempoPreparo.RAPIDO,
                            description = "Um milkshake cremoso e indulgente, feito com sorvete de baunilha e leite, e finalizado com uma generosa camada de chantilly e calda de chocolate."
                        ), Food(
                            id = 13,
                            restaurantId = 0L,
                            name = "Café",
                            price = 25F,
                            section = FoodSection.BEBIDA,
                            imageId = R.drawable.image_bebida_cafe,
                            timeForPrepare = TempoPreparo.RAPIDO,
                            description = "Café aromático e encorpado, preparado com grãos recém-torrados e moídos na hora, proporcionando um sabor rico e intenso que desperta os sentidos a cada gole."
                        ), Food(
                            id = 14,
                            restaurantId = 0L,
                            name = "Suco de Manga",
                            price = 27F,
                            section = FoodSection.BEBIDA,
                            imageId = R.drawable.image_bebida_suco_manga,
                            timeForPrepare = TempoPreparo.RAPIDO,
                            description = "Suco naturalmente doce e refrescante, feito com mangas maduras e suculentas, perfeito para saciar a sede e fornecer uma dose extra de vitaminas e antioxidantes."
                        ), Food(
                            id = 15,
                            restaurantId = 0L,
                            name = "Vinho",
                            price = 60F,
                            section = FoodSection.BEBIDA,
                            imageId = R.drawable.image_bebida_vinho,
                            timeForPrepare = TempoPreparo.RAPIDO,
                            description = "Vinho encorpado e elegante, com notas frutadas e taninos macios, ideal para acompanhar uma refeição sofisticada ou apreciar em uma noite especial."
                        ), Food(
                            id = 16,
                            restaurantId = 1L,
                            name = "Açaí",
                            price = 29F,
                            section = FoodSection.SOBREMESA,
                            imageId = R.drawable.image_sobremesa_acai,
                            timeForPrepare = TempoPreparo.RAPIDO,
                            description = "Uma tigela de açaí cremoso e nutritivo, coberto com frutas frescas, granola crocante e um fio de mel, proporcionando uma explosão de sabor e energia revitalizante."
                        ), Food(
                            id = 17,
                            restaurantId = 1L,
                            name = "Bolo de Cenoura",
                            price = 20F,
                            section = FoodSection.SOBREMESA,
                            imageId = R.drawable.image_sobremesa_bolo_cenoura,
                            timeForPrepare = TempoPreparo.RAPIDO,
                            description = "Bolo fofinho e úmido, repleto de cenoura ralada e nozes picadas, coberto com uma generosa camada de glacê de cream cheese, que derrete na boca em cada mordida."
                        ), Food(
                            id = 18,
                            restaurantId = 1L,
                            name = "Brigadeiro",
                            price = 10F,
                            section = FoodSection.SOBREMESA,
                            imageId = R.drawable.image_sobremesa_brigadeiro,
                            timeForPrepare = TempoPreparo.RAPIDO,
                            description = "Pequenas bolinhas de brigadeiro de chocolate, feitas com cacau em pó e leite condensado, cobertas com granulado colorido, uma sobremesa clássica que é simplesmente irresistível."
                        ), Food(
                            id = 19,
                            restaurantId = 1L,
                            name = "Brownie",
                            price = 23F,
                            section = FoodSection.SOBREMESA,
                            imageId = R.drawable.image_sobremesa_brownie,
                            timeForPrepare = TempoPreparo.RAPIDO,
                            description = "O brownie mais denso e indulgente, repleto de pedaços de chocolate meio amargo e nozes crocantes, perfeito para os amantes de chocolate que desejam uma dose extra de indulgência."
                        ), Food(
                            id = 20,
                            restaurantId = 2L,
                            name = "Coalhada",
                            price = 20F,
                            section = FoodSection.SOBREMESA,
                            imageId = R.drawable.image_sobremesa_coalhada,
                            timeForPrepare = TempoPreparo.RAPIDO,
                            description = "Coalhada cremosa e refrescante, feita com iogurte natural e temperada com ervas frescas e azeite de oliva, uma opção leve e saudável para acompanhar pães ou vegetais frescos."
                        ), Food(
                            id = 21,
                            restaurantId = 2L,
                            name = "Torta de Leite",
                            price = 17F,
                            section = FoodSection.SOBREMESA,
                            imageId = R.drawable.image_sobremesa_torta,
                            timeForPrepare = TempoPreparo.RAPIDO,
                            description = "Uma torta de leite macia e reconfortante, com uma base de massa amanteigada e um recheio cremoso de leite condensado, uma sobremesa clássica que evoca memórias de infância e conforto."
                        )
                    )
                )

                foodDao.insertAll(foods)
            }
        }
    }
}