package com.example.data.model

object DefaultCategories {
    val list = listOf(
        Category(
            id = "brands",
            nameEn = "Brands & Tech",
            nameBg = "Марки и Технологии",
            wordsEn = listOf(
                "Apple", "Nike", "Samsung", "BMW", "Adidas", "Coca-Cola", "Mercedes", "Sony",
                "Gucci", "McDonald's", "Audi", "Pepsi", "Tesla", "Rolex", "Zara", "Porsche",
                "Puma", "Netflix", "Google", "Amazon", "PlayStation", "Ferrari", "Toyota", "Chanel"
            ),
            wordsBg = listOf(
                "Apple", "Nike", "Samsung", "BMW", "Adidas", "Кока-Кола", "Мерцедес", "Sony",
                "Гучи", "Макдоналдс", "Ауди", "Пепси", "Тесла", "Ролекс", "Зара", "Порше",
                "Пума", "Нетфликс", "Гугъл", "Амазон", "Плейстейшън", "Ферари", "Тойота", "Шанел"
            ),
            iconName = "business"
        ),
        Category(
            id = "foods",
            nameEn = "Foods & Drinks",
            nameBg = "Храни и Напитки",
            wordsEn = listOf(
                "Pizza", "Banitsa", "Sushi", "Burger", "Pasta", "Chocolate", "Musaka", "Ice Cream",
                "Coffee", "Pancake", "Tarator", "Kebabche", "Soup", "Donut", "Tacos", "Croissant",
                "Steak", "Salad", "Wine", "Cappuccino", "Cheesecake", "Shawarma", "Ravioli", "Eclair"
            ),
            wordsBg = listOf(
                "Пица", "Баница", "Суши", "Бургер", "Паста", "Шоколад", "Мусака", "Сладолед",
                "Кафе", "Палачинка", "Таратор", "Кебапче", "Супа", "Донат", "Такос", "Кроасан",
                "Пържола", "Салата", "Вино", "Капучино", "Чийзкейк", "Дюнер", "Равиоли", "Еклер"
            ),
            iconName = "restaurant"
        ),
        Category(
            id = "animals",
            nameEn = "Animals & Wildlife",
            nameBg = "Животни и Природа",
            wordsEn = listOf(
                "Lion", "Eagle", "Bear", "Tiger", "Dolphin", "Elephant", "Wolf", "Penguin",
                "Kangaroo", "Owl", "Snake", "Shark", "Horse", "Panda", "Cheetah", "Crocodile",
                "Fox", "Octopus", "Chameleon", "Falcon", "Giraffe", "Gorilla", "Flamingo", "Beaver"
            ),
            wordsBg = listOf(
                "Лъв", "Орел", "Мечка", "Тигър", "Делфин", "Слон", "Вълк", "Пингвин",
                "Кенгуру", "Бухал", "Змия", "Акула", "Кон", "Панда", "Гепард", "Крокодил",
                "Лисица", "Октопод", "Хамелеон", "Сокол", "Жираф", "Горила", "Фламинго", "Бобър"
            ),
            iconName = "pets"
        ),
        Category(
            id = "movies",
            nameEn = "Movies & Shows",
            nameBg = "Филми и Сериали",
            wordsEn = listOf(
                "Inception", "Titanic", "Avatar", "Matrix", "Harry Potter", "Gladiator",
                "Star Wars", "Shrek", "Batman", "Spider-Man", "Interstellar", "Joker",
                "Breaking Bad", "Friends", "Stranger Things", "Game of Thrones", "Wednesday",
                "Oppenheimer", "Dune", "Peaky Blinders", "Sherlock", "Squid Game"
            ),
            wordsBg = listOf(
                "Inception", "Титаник", "Аватар", "Матрицата", "Хари Потър", "Гладиатор",
                "Междузвездни войни", "Шрек", "Батман", "Спайдър-мен", "Интерстелар", "Жокера",
                "Воопечещ", "Приятели", "Stranger Things", "Игра на тронове", "Уенздей",
                "Опенхаймер", "Дюн", "Остри козирки", "Шерлок", "Игра на калмари"
            ),
            iconName = "movie"
        ),
        Category(
            id = "games",
            nameEn = "Video Games",
            nameBg = "Видео Игри",
            wordsEn = listOf(
                "Minecraft", "Fortnite", "GTA V", "Witcher 3", "Pokemon", "Super Mario",
                "Zelda", "Tetris", "Counter-Strike", "League of Legends", "FIFA", "Roblox",
                "Valorant", "Cyberpunk", "God of War", "Pac-Man", "Elden Ring", "Overwatch"
            ),
            wordsBg = listOf(
                "Minecraft", "Fortnite", "GTA V", "Witcher 3", "Покемон", "Супер Марио",
                "Зелда", "Тетрис", "Counter-Strike", "League of Legends", "ФИФА", "Roblox",
                "Valorant", "Cyberpunk", "God of War", "Пак-ман", "Elden Ring", "Overwatch"
            ),
            iconName = "sports_esports"
        ),
        Category(
            id = "countries",
            nameEn = "Countries & Cities",
            nameBg = "Държави и Градове",
            wordsEn = listOf(
                "Bulgaria", "France", "Japan", "Sofia", "London", "Paris", "Rome", "Brazil",
                "Egypt", "New York", "Tokyo", "Greece", "Italy", "Spain", "Berlin", "Sydney",
                "Dubai", "Canada", "Turkey", "Norway", "Plovdiv", "Barcelona", "Amsterdam"
            ),
            wordsBg = listOf(
                "България", "Франция", "Япония", "София", "Лондон", "Париж", "Рим", "Бразилия",
                "Египет", "Ню Йорк", "Токио", "Гърция", "Италия", "Испания", "Берлин", "Сидни",
                "Дубай", "Канада", "Турция", "Норвегия", "Пловдив", "Барселона", "Амстердам"
            ),
            iconName = "public"
        ),
        Category(
            id = "objects",
            nameEn = "Objects & Gadgets",
            nameBg = "Предмети и Джаджи",
            wordsEn = listOf(
                "Smartphone", "Telescope", "Guitar", "Watch", "Drone", "Camera", "Laptop",
                "Bicycle", "Headphones", "VR Headset", "Microwave", "Console", "Projector",
                "Smart Ring", "Compass", "Submarine", "Binoculars", "Microphone", "Printer"
            ),
            wordsBg = listOf(
                "Смартфон", "Телескоп", "Китара", "Часовник", "Дрон", "Камера", "Лаптоп",
                "Велосипед", "Слушалки", "VR Очила", "Микровълнова", "Конзола", "Прожектор",
                "Смарт Пръстен", "Компас", "Подводница", "Бинокъл", "Микрофон", "Принтер"
            ),
            iconName = "devices"
        ),
        Category(
            id = "professions",
            nameEn = "Professions & Jobs",
            nameBg = "Професии и Работа",
            wordsEn = listOf(
                "Doctor", "Astronaut", "Chef", "Detective", "Pilot", "Firefighter", "Architect",
                "Artist", "Judge", "Scientist", "Police Officer", "Musician", "Hacker", "Lawyer",
                "Photographer", "Engineer", "Surgeon", "Journalist", "Plumber", "Veterinarian"
            ),
            wordsBg = listOf(
                "Лекар", "Астронавт", "Готвач", "Детектив", "Пилот", "Пожарникар", "Архитект",
                "Художник", "Съдия", "Учен", "Полицай", "Музикант", "Хакер", "Адвокат",
                "Фотограф", "Инженер", "Хирург", "Журналист", "Водопроводчик", "Ветеринар"
            ),
            iconName = "work"
        )
    )
}
