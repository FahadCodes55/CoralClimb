package com.example.data.generator

import com.example.data.model.Question

object EnglishMediumQuestions {
    val list: List<Question> = listOf(
        // Opposites (big/small)
        Question("What is the opposite of 'Big'?", listOf("Small", "Loud", "Heavy", "Green"), 0),
        Question("What is the opposite of 'Hot'?", listOf("Cold", "Tall", "Sweet", "Fast"), 0),
        Question("What is the opposite of 'Up'?", listOf("Down", "Near", "Wide", "Over"), 0),
        Question("What is the opposite of 'Happy'?", listOf("Sad", "Soft", "Round", "Pink"), 0),
        Question("What is the opposite of 'Fast'?", listOf("Slow", "High", "Clean", "Loud"), 0),
        Question("What is the opposite of 'Day'?", listOf("Night", "Morning", "Sun", "Noon"), 0),
        Question("What is the opposite of 'Open'?", listOf("Closed", "Bright", "Far", "Kind"), 0),
        Question("What is the opposite of 'Quiet'?", listOf("Loud", "Calm", "Shy", "Soft"), 0),
        Question("What is the opposite of 'Clean'?", listOf("Dirty", "Shiny", "Bright", "Neat"), 0),
        Question("What is the opposite of 'Soft'?", listOf("Hard", "Warm", "Gentle", "Fluffy"), 0),
        Question("What is the opposite of 'Tall'?", listOf("Short", "Long", "Wide", "Huge"), 0),
        Question("What is the opposite of 'Light'?", listOf("Dark", "Bright", "Clear", "Sunny"), 0),
        Question("What is the opposite of 'Near'?", listOf("Far", "Close", "Deep", "Next"), 0),
        Question("What is the opposite of 'Young'?", listOf("Old", "New", "Fresh", "Small"), 0),
        Question("What is the opposite of 'Good'?", listOf("Bad", "Nice", "Kind", "Fair"), 0),

        // Singular and plural (one cat, two ___)
        Question("One cat, two ___", listOf("cats", "cat", "cates", "cating"), 0),
        Question("One dog, two ___", listOf("dogs", "dog", "doges", "doging"), 0),
        Question("One bird, two ___", listOf("birds", "bird", "birdes", "birdies"), 0),
        Question("One fish, two ___", listOf("fish", "fishes", "fishs", "fishing"), 0),
        Question("One book, two ___", listOf("books", "book", "bookes", "booking"), 0),
        Question("One ball, two ___", listOf("balls", "ball", "balles", "balling"), 0),
        Question("One frog, two ___", listOf("frogs", "frog", "froges", "frogging"), 0),
        Question("One tree, two ___", listOf("trees", "tree", "treeses", "treeing"), 0),
        Question("One duck, two ___", listOf("ducks", "duck", "duckes", "ducking"), 0),
        Question("One star, two ___", listOf("stars", "star", "stares", "staring"), 0),
        Question("One boat, two ___", listOf("boats", "boat", "boates", "boating"), 0),
        Question("One apple, two ___", listOf("apples", "apple", "appls", "appling"), 0),
        Question("One shoe, two ___", listOf("shoes", "shoe", "shoos", "shoing"), 0),
        Question("One cup, two ___", listOf("cups", "cup", "cupes", "cupping"), 0),
        Question("One child, two ___", listOf("children", "childs", "childes", "childrens"), 0),

        // Unscramble a 4-letter word
        Question("Unscramble: b - a - l - l", listOf("Ball", "Bell", "Bull", "Bill"), 0),
        Question("Unscramble: f - i - s - h", listOf("Fish", "Fast", "Frog", "Fork"), 0),
        Question("Unscramble: d - u - c - k", listOf("Duck", "Desk", "Dark", "Dock"), 0),
        Question("Unscramble: f - r - o - g", listOf("Frog", "Flag", "Fork", "Food"), 0),
        Question("Unscramble: s - t - a - r", listOf("Star", "Stop", "Step", "Stay"), 0),
        Question("Unscramble: b - o - a - t", listOf("Boat", "Boot", "Bait", "Beat"), 0),
        Question("Unscramble: b - o - o - k", listOf("Book", "Bark", "Back", "Bank"), 0),
        Question("Unscramble: t - r - e - e", listOf("Tree", "Tire", "Time", "Team"), 0),
        Question("Unscramble: m - i - l - k", listOf("Milk", "Mark", "Mask", "Make"), 0),
        Question("Unscramble: b - i - r - d", listOf("Bird", "Bake", "Bell", "Barn"), 0),
        Question("Unscramble: c - a - k - e", listOf("Cake", "Cook", "Camp", "Corn"), 0),
        Question("Unscramble: d - o - o - r", listOf("Door", "Deer", "Drum", "Duck"), 0),
        Question("Unscramble: h - o - m - e", listOf("Home", "Hand", "Hill", "Hawk"), 0),
        Question("Unscramble: p - a - r - k", listOf("Park", "Pony", "Pool", "Pond"), 0),

        // Fill in missing word in a very short sentence
        Question("The frog can ___ in the pond.", listOf("jump", "bake", "drive", "type"), 0),
        Question("Birds ___ high in the blue sky.", listOf("fly", "sail", "walk", "sleep"), 0),
        Question("Fish ___ happily in the water.", listOf("swim", "read", "climb", "bark"), 0),
        Question("The sun is warm and ___.", listOf("bright", "sour", "cold", "foggy"), 0),
        Question("I ___ an apple every day.", listOf("eat", "fly", "wear", "drive"), 0),
        Question("The dog likes to ___ the ball.", listOf("catch", "wash", "color", "sing"), 0),
        Question("We ___ nice books at school.", listOf("read", "cook", "brush", "jump"), 0),
        Question("She drinks a cold glass of ___.", listOf("milk", "sand", "pencil", "paper"), 0),
        Question("The baby went to ___ in the bed.", listOf("sleep", "drive", "swim", "fly"), 0),
        Question("We wash our hands with warm ___.", listOf("water", "rocks", "grass", "wood"), 0),
        Question("The turtle crawls very ___.", listOf("slowly", "loudly", "tall", "blue"), 0),

        // Spelling of common words
        Question("Pick the correct spelling:", listOf("Friend", "Frend", "Freind", "Frind"), 0),
        Question("Pick the correct spelling:", listOf("Water", "Watter", "Watar", "Watr"), 0),
        Question("Pick the correct spelling:", listOf("School", "Scool", "Skool", "Schol"), 0),
        Question("Pick the correct spelling:", listOf("Happy", "Hapy", "Happie", "Happi"), 0),
        Question("Pick the correct spelling:", listOf("Little", "Littel", "Litle", "Litte"), 0),
        Question("Pick the correct spelling:", listOf("Because", "Becos", "Becaws", "Bcause"), 0),
        Question("Pick the correct spelling:", listOf("Mother", "Mothur", "Muthur", "Mothar"), 0),
        Question("Pick the correct spelling:", listOf("Yellow", "Yelow", "Yello", "Yallow"), 0),
        Question("Pick the correct spelling:", listOf("Turtle", "Turtel", "Tortle", "Tertle"), 0),
        Question("Pick the correct spelling:", listOf("Animal", "Anamal", "Animel", "Aminal"), 0),
        Question("Pick the correct spelling:", listOf("Family", "Famly", "Fammily", "Familie"), 0)
    )
}
