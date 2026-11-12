package com.example.data.generator

import com.example.data.model.Question

object EnglishHardQuestions {
    val list: List<Question> = listOf(
        // Verbs
        Question("Which word is an action verb?", listOf("Jump", "Blue", "Pencil", "Cloud"), 0),
        Question("Which word is an action verb?", listOf("Dance", "Apple", "Chair", "Table"), 0),
        Question("Which word is an action verb?", listOf("Listen", "Quiet", "Music", "Soft"), 0),
        Question("Which word is an action verb?", listOf("Build", "House", "Bricks", "Heavy"), 0),
        Question("Which word is an action verb?", listOf("Write", "Paper", "Story", "Desk"), 0),
        Question("Which word is an action verb?", listOf("Explore", "Island", "Map", "Cave"), 0),
        Question("Choose the verb: 'Dolphins leap high.'", listOf("leap", "high", "Dolphins", "water"), 0),
        Question("Choose the verb: 'The ship sailed away.'", listOf("sailed", "away", "ship", "The"), 0),
        Question("Choose the verb: 'Birds sing sweet songs.'", listOf("sing", "sweet", "songs", "Birds"), 0),
        Question("Choose the verb: 'The diver swims deep.'", listOf("swims", "deep", "diver", "The"), 0),
        Question("Choose the verb: 'Stars shine at night.'", listOf("shine", "night", "Stars", "dark"), 0),
        Question("Choose the verb: 'Children play outside.'", listOf("play", "outside", "Children", "fun"), 0),
        Question("Choose the verb: 'Frogs catch bugs.'", listOf("catch", "bugs", "Frogs", "pond"), 0),

        // Adjectives
        Question("Which word is an adjective?", listOf("Colorful", "Color", "Paint", "Brush"), 0),
        Question("Which word is an adjective?", listOf("Gentle", "Breeze", "Wind", "Blow"), 0),
        Question("Which word is an adjective?", listOf("Bright", "Sun", "Shine", "Sky"), 0),
        Question("Which word is an adjective?", listOf("Huge", "Whale", "Ocean", "Water"), 0),
        Question("Which word is an adjective?", listOf("Playful", "Play", "Game", "Toy"), 0),
        Question("Which word is an adjective?", listOf("Sweet", "Sugar", "Candy", "Bake"), 0),
        Question("Choose the adjective: 'The brave diver explored.'", listOf("brave", "diver", "explored", "The"), 0),
        Question("Choose the adjective: 'We saw a giant turtle.'", listOf("giant", "turtle", "saw", "We"), 0),
        Question("Choose the adjective: 'The clear water was cold.'", listOf("clear", "water", "was", "sea"), 0),
        Question("Choose the adjective: 'She wore a warm jacket.'", listOf("warm", "jacket", "wore", "She"), 0),
        Question("Choose the adjective: 'A curious dolphin swam by.'", listOf("curious", "dolphin", "swam", "by"), 0),
        Question("Choose the adjective: 'The little fish hid away.'", listOf("little", "fish", "hid", "away"), 0),
        Question("Choose the adjective: 'Fresh fruit tastes yummy.'", listOf("Fresh", "fruit", "tastes", "food"), 0),

        // Synonyms
        Question("What is a synonym for 'Glad'?", listOf("Happy", "Tired", "Angry", "Cold"), 0),
        Question("What is a synonym for 'Quick'?", listOf("Fast", "Slow", "Heavy", "Dull"), 0),
        Question("What is a synonym for 'Shut'?", listOf("Close", "Open", "Break", "Throw"), 0),
        Question("What is a synonym for 'Smart'?", listOf("Clever", "Silly", "Clumsy", "Weak"), 0),
        Question("What is a synonym for 'Large'?", listOf("Big", "Small", "Tiny", "Narrow"), 0),
        Question("What is a synonym for 'Tiny'?", listOf("Small", "Huge", "Giant", "Heavy"), 0),
        Question("What is a synonym for 'Brave'?", listOf("Courageous", "Afraid", "Scared", "Shy"), 0),
        Question("What is a synonym for 'Silent'?", listOf("Quiet", "Loud", "Noisy", "Wild"), 0),
        Question("What is a synonym for 'Neat'?", listOf("Tidy", "Messy", "Dirty", "Broken"), 0),
        Question("What is a synonym for 'Simple'?", listOf("Easy", "Hard", "Difficult", "Strange"), 0),
        Question("What is a synonym for 'Begin'?", listOf("Start", "Stop", "End", "Wait"), 0),
        Question("What is a synonym for 'Finish'?", listOf("End", "Start", "Begin", "Open"), 0),
        Question("What is a synonym for 'Gleam'?", listOf("Shine", "Fade", "Darken", "Hide"), 0),

        // Past Tense
        Question("What is the past tense of 'Run'?", listOf("Ran", "Runned", "Running", "Ranned"), 0),
        Question("What is the past tense of 'Swim'?", listOf("Swam", "Swimmed", "Swum", "Swimming"), 0),
        Question("What is the past tense of 'Eat'?", listOf("Ate", "Eated", "Eating", "Aten"), 0),
        Question("What is the past tense of 'See'?", listOf("Saw", "Seed", "Seeing", "Seen"), 0),
        Question("What is the past tense of 'Go'?", listOf("Went", "Goed", "Going", "Gone"), 0),
        Question("What is the past tense of 'Fly'?", listOf("Flew", "Flyed", "Flying", "Flown"), 0),
        Question("What is the past tense of 'Catch'?", listOf("Caught", "Catched", "Catching", "Cot"), 0),
        Question("What is the past tense of 'Sing'?", listOf("Sang", "Singed", "Singing", "Song"), 0),
        Question("What is the past tense of 'Grow'?", listOf("Grew", "Growed", "Growing", "Grown"), 0),
        Question("What is the past tense of 'Bring'?", listOf("Brought", "Brang", "Bringed", "Broughted"), 0),
        Question("What is the past tense of 'Take'?", listOf("Took", "Taked", "Taking", "Token"), 0),
        Question("What is the past tense of 'Write'?", listOf("Wrote", "Writed", "Writing", "Written"), 0),
        Question("What is the past tense of 'Find'?", listOf("Found", "Finded", "Finding", "Founden"), 0),

        // Sentence Completion
        Question("The sea turtle swam ___ through the clear water.", listOf("gracefully", "loud", "sandwich", "yesterday"), 0),
        Question("We must ___ quietly when reading in the library.", listOf("sit", "shout", "dance", "sleep"), 0),
        Question("The cheerful puppy was ___ to see its friends.", listOf("excited", "bored", "angry", "asleep"), 0),
        Question("She shared her toys because she is ___.", listOf("kind", "greedy", "mean", "grumpy"), 0),
        Question("The submarine descended ___ into the calm sea.", listOf("safely", "dangerously", "rough", "noisy"), 0),
        Question("Children built a big sandcastle on the sunny ___.", listOf("beach", "kitchen", "sofa", "closet"), 0),
        Question("The diver checked his tank before taking the ___.", listOf("dive", "nap", "ride", "bus"), 0),
        Question("Always look both ways before you ___ the street.", listOf("cross", "sleep", "sing", "write"), 0),
        Question("Bright stars ___ in the clear night sky.", listOf("twinkled", "barked", "melted", "cried"), 0),
        Question("He solved the math puzzle with great ___.", listOf("care", "shoes", "sand", "water"), 0),
        Question("The coral reef is a healthy home for sea ___.", listOf("life", "cars", "tables", "spoons"), 0),
        Question("We packed our warm coats for the winter ___.", listOf("trip", "dinner", "spoon", "desk"), 0),
        Question("Gentle waves washed shells onto the warm ___.", listOf("sand", "bed", "ceiling", "cloud"), 0)
    )
}
