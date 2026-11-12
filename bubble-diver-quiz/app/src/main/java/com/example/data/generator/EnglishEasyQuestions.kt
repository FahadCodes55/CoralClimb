package com.example.data.generator

import com.example.data.model.Question

object EnglishEasyQuestions {
    val list: List<Question> = listOf(
        // Missing letter in a 3-letter word
        Question("Fill in the missing letter: c _ t", listOf("a", "m", "z", "p"), 0),
        Question("Fill in the missing letter: d _ g", listOf("o", "k", "t", "r"), 0),
        Question("Fill in the missing letter: s _ n", listOf("u", "b", "p", "d"), 0),
        Question("Fill in the missing letter: b _ x", listOf("o", "l", "m", "s"), 0),
        Question("Fill in the missing letter: p _ g", listOf("i", "k", "t", "w"), 0),
        Question("Fill in the missing letter: h _ t", listOf("a", "x", "f", "q"), 0),
        Question("Fill in the missing letter: b _ d", listOf("e", "k", "v", "j"), 0),
        Question("Fill in the missing letter: f _ x", listOf("o", "b", "c", "d"), 0),
        Question("Fill in the missing letter: b _ s", listOf("u", "g", "z", "y"), 0),
        Question("Fill in the missing letter: c _ p", listOf("u", "x", "m", "n"), 0),
        Question("Fill in the missing letter: p _ n", listOf("e", "w", "b", "k"), 0),
        Question("Fill in the missing letter: r _ n", listOf("u", "q", "j", "z"), 0),
        Question("Fill in the missing letter: b _ g", listOf("a", "x", "p", "f"), 0),
        Question("Fill in the missing letter: l _ g", listOf("e", "q", "z", "m"), 0),

        // Which word starts with a given letter
        Question("Which word starts with letter B?", listOf("Ball", "Cat", "Sun", "Dog"), 0),
        Question("Which word starts with letter S?", listOf("Sun", "Fish", "Moon", "Tree"), 0),
        Question("Which word starts with letter M?", listOf("Monkey", "Duck", "Lion", "Bear"), 0),
        Question("Which word starts with letter D?", listOf("Duck", "Frog", "Fish", "Bird"), 0),
        Question("Which word starts with letter F?", listOf("Fish", "Ship", "Boat", "Car"), 0),
        Question("Which word starts with letter T?", listOf("Turtle", "Rabbit", "Zebra", "Horse"), 0),
        Question("Which word starts with letter A?", listOf("Apple", "Banana", "Orange", "Grape"), 0),
        Question("Which word starts with letter C?", listOf("Cat", "Dog", "Pig", "Cow"), 0),
        Question("Which word starts with letter P?", listOf("Pig", "Hen", "Fox", "Wolf"), 0),
        Question("Which word starts with letter R?", listOf("Red", "Blue", "Pink", "Gold"), 0),
        Question("Which word starts with letter H?", listOf("Hat", "Cap", "Shoe", "Sock"), 0),
        Question("Which word starts with letter L?", listOf("Lion", "Tiger", "Bear", "Wolf"), 0),
        Question("Which word starts with letter W?", listOf("Water", "Sand", "Rock", "Dirt"), 0),

        // Pick the correct spelling of a simple word
        Question("Which spelling is correct?", listOf("Fish", "Fsh", "Fihs", "Fishe"), 0),
        Question("Which spelling is correct?", listOf("Cat", "Catt", "Cta", "Katt"), 0),
        Question("Which spelling is correct?", listOf("Book", "Bok", "Buke", "Boke"), 0),
        Question("Which spelling is correct?", listOf("Milk", "Mlik", "Melk", "Milkk"), 0),
        Question("Which spelling is correct?", listOf("Tree", "Tre", "Trea", "Trie"), 0),
        Question("Which spelling is correct?", listOf("Star", "Starr", "Srat", "Staar"), 0),
        Question("Which spelling is correct?", listOf("Duck", "Duk", "Dck", "Duckk"), 0),
        Question("Which spelling is correct?", listOf("Boat", "Bote", "Bota", "Boatt"), 0),
        Question("Which spelling is correct?", listOf("Frog", "Frogg", "Frgo", "Fraug"), 0),
        Question("Which spelling is correct?", listOf("Baby", "Babi", "Babee", "Babie"), 0),
        Question("Which spelling is correct?", listOf("Blue", "Blew", "Blu", "Bloo"), 0),
        Question("Which spelling is correct?", listOf("Pink", "Pnik", "Penk", "Pinc"), 0),

        // Which word names an animal
        Question("Which word is an animal?", listOf("Turtle", "Cup", "Bed", "Spoon"), 0),
        Question("Which word is an animal?", listOf("Rabbit", "Table", "Door", "Chair"), 0),
        Question("Which word is an animal?", listOf("Horse", "Pencil", "Plate", "Clock"), 0),
        Question("Which word is an animal?", listOf("Dolphin", "Blanket", "Shoe", "Window"), 0),
        Question("Which word is an animal?", listOf("Monkey", "Fork", "Carrot", "Bottle"), 0),
        Question("Which word is an animal?", listOf("Lion", "Shirt", "Pants", "Sock"), 0),
        Question("Which word is an animal?", listOf("Bear", "Fork", "Plate", "Desk"), 0),
        Question("Which word is an animal?", listOf("Duck", "Pillow", "Towel", "Soap"), 0),

        // Which word names a color
        Question("Which word is a color?", listOf("Blue", "Chair", "Shoe", "Tree"), 0),
        Question("Which word is a color?", listOf("Red", "Ball", "Fork", "Book"), 0),
        Question("Which word is a color?", listOf("Green", "House", "Car", "Ship"), 0),
        Question("Which word is a color?", listOf("Yellow", "Spoon", "Plate", "Window"), 0),
        Question("Which word is a color?", listOf("Pink", "Clock", "Bread", "Water"), 0),
        Question("Which word is a color?", listOf("Purple", "Pencil", "Door", "Cup"), 0),
        Question("Which word is a color?", listOf("White", "Bottle", "Brush", "Blanket"), 0),
        Question("Which word is a color?", listOf("Brown", "Flower", "Cloud", "Grass"), 0),

        // Which word rhymes with a given word
        Question("Which word rhymes with 'Cat'?", listOf("Hat", "Dog", "Pig", "Sun"), 0),
        Question("Which word rhymes with 'Sun'?", listOf("Run", "Star", "Moon", "Sky"), 0),
        Question("Which word rhymes with 'Fish'?", listOf("Dish", "Frog", "Crab", "Ship"), 0),
        Question("Which word rhymes with 'Dog'?", listOf("Frog", "Cat", "Duck", "Cow"), 0),
        Question("Which word rhymes with 'Bed'?", listOf("Red", "Blue", "Hot", "Big"), 0),
        Question("Which word rhymes with 'Star'?", listOf("Car", "Tree", "Bird", "Boat"), 0),
        Question("Which word rhymes with 'Tree'?", listOf("Bee", "Ant", "Fly", "Worm"), 0),
        Question("Which word rhymes with 'Boat'?", listOf("Coat", "Shoe", "Sock", "Hat"), 0),
        Question("Which word rhymes with 'Ball'?", listOf("Tall", "Short", "Fast", "Cold"), 0),
        Question("Which word rhymes with 'Cake'?", listOf("Lake", "Milk", "Juice", "Bread"), 0),
        Question("Which word rhymes with 'Fox'?", listOf("Box", "Bag", "Cup", "Pan"), 0),
        Question("Which word rhymes with 'Duck'?", listOf("Truck", "Train", "Plane", "Ship"), 0)
    )
}
