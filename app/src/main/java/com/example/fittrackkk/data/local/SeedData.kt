package com.example.fittrackkk.data.local

import com.example.fittrackkk.data.model.*

object SeedData {

    fun getDietDays(): List<DietDay> {
        val meals = listOf(
            arrayOf("Sel Roti with Aloo Dum", "450", "Dal Bhat (Rice, Lentils, Veggies)", "600", "Roasted Soybeans (Bhatmas)", "150", "Chicken Curry with Roti", "550"),
            arrayOf("Oatmeal with Milk & Banana", "350", "Dhido with Local Chicken", "650", "Fresh Sliced Guava", "80", "Mixed Vegetable Thukpa", "480"),
            arrayOf("Boiled Eggs & Cornbread", "400", "Newari Khaja Set (Samay Baji)", "700", "Lassi (Yogurt Drink)", "200", "Paneer Butter Masala & Naan", "600"),
            arrayOf("Chana Fried with Tea", "380", "Mutton Dal Bhat", "750", "Makhana (Fox Nuts)", "120", "Grilled Fish with Steamed Veggies", "500"),
            arrayOf("Pancakes with Honey", "420", "Veg Momo (10 pcs)", "450", "Yogurt with Chopped Mango", "180", "Egg Curry with Rice", "550"),
            arrayOf("Baru (Roasted Flour) Porridge", "350", "Gundruk Dhido Set", "580", "Cucumber & Carrots", "60", "Chicken Chowmein", "520"),
            arrayOf("Toast with Peanut Butter", "320", "Fish Curry with Rice", "600", "Apple Slices", "90", "Aloo Kauli (Potato & Cauliflower)", "450"),
            arrayOf("Bread Omelette", "380", "Buff Momo (Steamed)", "500", "Roasted Corn", "140", "Sukuti (Dried Meat) Stir-fry", "480"),
            arrayOf("Semolina (Suij) Halwa", "400", "Thakali Dal Bhat Set", "800", "Mixed Fruit Bowl", "150", "Mushroom Matar with Roti", "500"),
            arrayOf("Spiced Chickpeas (Chana)", "360", "Chicken Biryani", "700", "Tea with Digestive Biscuits", "180", "Vegetable Soup & Garlic Bread", "400"),
            arrayOf("Puri Tarkari", "480", "Keema Noodles", "550", "Peanuts", "160", "Grilled Chicken with Salad", "500"),
            arrayOf("Muesli with Curd", "340", "Potato Curry with Beaten Rice", "450", "Pomegranate", "110", "Rajma Chawal (Red Beans & Rice)", "550"),
            arrayOf("Scrambled Eggs & Toast", "400", "Chicken Thukpa", "520", "Banana", "100", "Bhindi Fry with Roti", "420"),
            arrayOf("Vegetable Pulao", "450", "Mutton Curry & Rice", "750", "Orange", "70", "Egg Fried Rice", "540"),
            arrayOf("French Toast", "420", "Mixed Veg Dal Bhat", "600", "Cashews & Raisins", "200", "Chicken Tikka with Salad", "480"),
            arrayOf("Upma with Veggies", "380", "Veg Chowmein", "480", "Yogurt", "120", "Soybean Curry with Rice", "500"),
            arrayOf("Banana Smoothie", "300", "Aloo Tama (Bamboo Shoot Curry)", "420", "Roasted Gram", "130", "Grilled Salmon & Asparagus", "550"),
            arrayOf("Boiled Corn", "180", "Chicken Momo (Jhol)", "550", "Walnuts", "190", "Mixed Bean Soup (Kwati)", "450"),
            arrayOf("Paratha with Curd", "450", "Egg Thukpa", "480", "Papaya", "80", "Mutton Sekuwa & Salad", "520"),
            arrayOf("Sprouts Salad", "250", "Fish Fried with Rice", "620", "Tea with Crackers", "160", "Paneer Tikka Masala", "580"),
            arrayOf("Cornflakes with Milk", "320", "Buff Thukpa", "500", "Almonds", "170", "Cabbage Potato Curry", "400"),
            arrayOf("Omelette with Cheese", "400", "Dal Makhani & Roti", "550", "Grapes", "100", "Roast Chicken & Potatoes", "580"),
            arrayOf("Dosa with Chutney", "380", "Veg Pulao with Raita", "500", "Roasted Peanuts", "160", "Chicken Kofta Curry", "550"),
            arrayOf("Smoothie Bowl", "350", "Kadai Paneer & Naan", "620", "Melon Slices", "70", "Vegetable Stew", "380"),
            arrayOf("Idli Sambar", "340", "Mutton Biryani", "800", "Tea & Rusk", "170", "Grilled Prawns & Veggies", "500"),
            arrayOf("Sandwich (Veg & Cheese)", "400", "Mixed Fried Rice", "550", "Pear", "90", "Aloo Matar & Roti", "450"),
            arrayOf("Porridge with Honey", "330", "Chicken Curry & Beaten Rice", "520", "Dried Figs", "180", "Egg Salad with Avocado", "420"),
            arrayOf("Boiled Eggs (2)", "150", "Veg Thali Set", "650", "Lassi", "200", "Baked Chicken Thighs", "520"),
            arrayOf("Spiced Potatoes", "350", "Buff Keema Noodles", "580", "Apricots", "100", "Chickpea Stew (Chana Masala)", "480"),
            arrayOf("Nepalese Breakfast Platter", "550", "Grand Dal Bhat Feast", "850", "Fruit & Nut Mix", "250", "Chef's Special Dinner", "650")
        )
        return meals.mapIndexed { index, m ->
            DietDay(
                dayNumber = index + 1,
                breakfast = m[0], breakfastCalories = m[1].toInt(),
                lunch = m[2], lunchCalories = m[3].toInt(),
                afternoonSnack = m[4], afternoonSnackCalories = m[5].toInt(),
                dinner = m[6], dinnerCalories = m[7].toInt()
            )
        }
    }

    fun getExercises(): List<Exercise> = listOf(
        Exercise(1, "Jumping Jacks", "Full body warm-up exercise", 60, "Cardio", "Beginner", 8),
        Exercise(2, "Push-ups", "Upper body strength exercise", 90, "Strength", "Beginner", 7),
        Exercise(3, "Squats", "Lower body compound exercise", 90, "Strength", "Beginner", 8),
        Exercise(4, "Lunges", "Single leg strengthening", 90, "Strength", "Beginner", 7),
        Exercise(5, "Plank", "Core stabilization hold", 60, "Core", "Beginner", 5),
        Exercise(6, "Burpees", "Full body explosive exercise", 60, "Cardio", "Intermediate", 10),
        Exercise(7, "Mountain Climbers", "Core & cardio combination", 60, "Cardio", "Intermediate", 9),
        Exercise(8, "Glute Bridges", "Posterior chain activation", 90, "Strength", "Beginner", 6),
        Exercise(9, "High Knees", "Running in place with high knees", 60, "Cardio", "Beginner", 8),
        Exercise(10, "Bicycle Crunches", "Rotational core exercise", 60, "Core", "Intermediate", 6),
        Exercise(11, "Tricep Dips", "Upper body push exercise", 90, "Strength", "Intermediate", 6),
        Exercise(12, "Side Plank", "Oblique stabilization", 60, "Core", "Intermediate", 4),
        Exercise(13, "Wall Sit", "Isometric leg exercise", 60, "Strength", "Beginner", 5),
        Exercise(14, "Calf Raises", "Lower leg strengthening", 60, "Strength", "Beginner", 4),
        Exercise(15, "Superman Hold", "Back strengthening exercise", 60, "Core", "Beginner", 5),
        Exercise(16, "Box Jumps", "Plyometric leg exercise", 60, "Cardio", "Intermediate", 9),
        Exercise(17, "Russian Twists", "Oblique rotational exercise", 60, "Core", "Intermediate", 6),
        Exercise(18, "Leg Raises", "Lower abdominal exercise", 60, "Core", "Intermediate", 5),
        Exercise(19, "Skater Jumps", "Lateral plyometric exercise", 60, "Cardio", "Intermediate", 8),
        Exercise(20, "Dead Bug", "Core coordination exercise", 60, "Core", "Beginner", 4)
    )

    fun getExerciseDays(): List<ExerciseDay> = (1..30).map { day ->
        val allIds = (1..20).shuffled().take(8)
        ExerciseDay(dayNumber = day, exerciseIds = allIds.joinToString(","))
    }

    fun getRecipes(): List<Recipe> = listOf(
        Recipe(1, "Authentic Dal Bhat", "Lunch", "1 cup Basmati rice\n1/2 cup Yellow lentils (Dal)\nSeasonal vegetables (Cauliflower, Potato, Mustard greens)\nSpices (Turmeric, Ginger, Garlic, Cumin)\nGhee", "1. Cook rice in a pressure cooker or pot.\n2. Boil lentils with turmeric and salt. Temper with ghee, garlic, and cumin.\n3. Stir-fry vegetables with spices.\n4. Serve hot with a side of pickle (Achar).", 650, 20, "dal_bhat"),
        Recipe(2, "Chicken Momo (Dumplings)", "Dinner", "500g Chicken mince\nMomo wrappers (flour based)\nChives, Ginger, Garlic\nMomo Masala\nSesame Tomato dipping sauce", "1. Mix chicken with finely chopped onions, chives, and spices.\n2. Place a spoonful in the center of a wrapper and pleat.\n3. Steam for 10-12 minutes.\n4. Serve with spicy tomato chutney.", 550, 25, "momo"),
        Recipe(3, "Gundruk ko Achar", "Side Dish", "Dried fermented leafy greens (Gundruk)\nRoasted soybeans\nOnions, Green chilies\nLemon juice\nMustard oil", "1. Soak Gundruk in water for 10 min.\n2. Mix with chopped onions, chilies, and roasted soybeans.\n3. Add oil, salt, and lemon juice.\n4. Perfect side for Dal Bhat.", 120, 10, "gundruk"),
        Recipe(4, "Chicken Choila", "Appetizer", "500g Chicken breast\nRoasted tomatoes\nGinger, Garlic, Green chilies\nMustard oil\nFenugreek seeds", "1. Grill chicken until charred and slice into small pieces.\n2. Blend roasted tomatoes, ginger, and garlic into a paste.\n3. Mix chicken with the paste and spices.\n4. Temper with hot mustard oil and fenugreek seeds.", 450, 15, "choila"),
        Recipe(5, "Sel Roti (Rice Bread)", "Breakfast", "Rice flour\nSugar\nGhee\nCardamom powder\nWater/Milk", "1. Mix ingredients into a thick, smooth batter. Let rest for 2 hours.\n2. Heat oil in a deep pan.\n3. Pour batter in a circular ring shape.\n4. Fry until golden brown and crispy on both sides.", 350, 30, "sel_roti"),
        Recipe(6, "Kwati (Mixed Bean Soup)", "Dinner", "Mixed sprouted beans (9 types)\nOnions, Ginger, Garlic\nBay leaves, Cumin seeds\nTurmeric, Salt", "1. Soak beans for 12 hours and let them sprout.\n2. Sauté onions and aromatics in a pressure cooker.\n3. Add sprouted beans and water.\n4. Cook until tender. Serve hot during cold days.", 420, 20, "kwati"),
        Recipe(7, "Alu Tama (Bamboo Shoot Curry)", "Lunch", "Fermented bamboo shoots (Tama)\nBlack-eyed peas\nPotatoes\nGinger, Garlic, Cumin powder", "1. Boil black-eyed peas until soft.\n2. Fry potatoes and add bamboo shoots.\n3. Mix in cooked peas and water. Simmer with spices.\n4. A classic sour and savory Nepalese curry.", 380, 20, "alu_tama"),
        Recipe(8, "Suij ko Halwa", "Breakfast", "1 cup Semolina (Suij)\n1/2 cup Sugar\n1/2 cup Ghee\nDry fruits (Almonds, Raisins)\nCardamom powder", "1. Roast semolina in ghee until light brown and fragrant.\n2. Add dry fruits and hot water mixed with sugar.\n3. Stir continuously until thick.\n4. Garnish with more nuts and serve warm.", 400, 15, "halwa"),
        Recipe(9, "Thukpa (Noodle Soup)", "Dinner", "Noodles\nChicken or Veggies\nGinger-Garlic paste\nGreen chilies, Coriander\nTomato base soup", "1. Boil noodles and set aside.\n2. Sauté ginger, garlic, and protein/veggies.\n3. Add water and tomato paste to create a broth.\n4. Combine noodles and broth. Season with salt and pepper.", 480, 15, "thukpa"),
        Recipe(10, "Bara (Lentil Pancake)", "Snack", "Black lentil flour\nGinger, Garlic paste\nSalt\nOptional: Egg or Keema topping", "1. Mix lentil flour with water and aromatics to make a thick paste.\n2. Heat oil on a flat pan.\n3. Shape into thick pancakes and fry until crispy.\n4. Add an egg on top for extra protein.", 320, 20, "bara"),
        Recipe(11, "Chataamari (Newari Pizza)", "Snack", "Rice flour\nMinced meat (Buff/Chicken)\nEgg\nOnion, Ginger, Garlic\nCumin, Salt", "1. Make a thin batter with rice flour and water.\n2. Pour onto a greased pan and spread thinly.\n3. Top with seasoned minced meat and a cracked egg.\n4. Cover and cook until the base is crispy and topping is set.", 450, 15, "chataamari"),
        Recipe(12, "Yomari", "Dessert", "Rice flour (for dough)\nChaku (Molasses) or Khuwa (Milk solids)\nSesame seeds", "1. Create a dough from rice flour and hot water.\n2. Shape into a hollow cone or teardrop.\n3. Fill with chaku and sesame seeds mix.\n4. Steam for 10-15 minutes until firm.", 380, 45, "yomari"),
    )

    fun getHealthArticles(): List<HealthArticle> = listOf(
        HealthArticle(1, "Nutritional Benefits of Dal Bhat", "Dal Bhat is the quintessential Nepalese meal, providing a balanced mix of complex carbohydrates (rice), protein (lentils), and essential vitamins and minerals (seasonal vegetables). This combination ensures sustained energy throughout the day. Lentils are high in fiber, which aids digestion and heart health. Adding a variety of local greens like 'Rayo ko Saag' boosts the intake of Vitamin K and Iron.", 4f, "Nutrition"),
        HealthArticle(2, "The Power of Sprouted Beans (Kwati)", "Kwati is a traditional Nepalese soup consisting of nine types of sprouted beans. Sprouting increases the bioavailability of nutrients and makes beans easier to digest. It is exceptionally high in protein and fiber, making it an excellent post-workout meal. Kwati is also rich in antioxidants and helps in body detoxification.", 3f, "Nutrition"),
        HealthArticle(3, "Health Benefits of Gundruk", "Gundruk is fermented leafy green vegetables, a staple in Nepalese households. The fermentation process introduces beneficial probiotics that support gut health and strengthen the immune system. It is also an excellent source of lactic acid and minerals. Gundruk is a low-calorie addition to any meal, providing a unique flavor profile without extra fat.", 3f, "Health"),
        HealthArticle(4, "High Energy Foods for Mountain Living", "Living in hilly or mountainous regions of Nepal requires high-energy expenditure. Foods like Dhido (buckwheat or millet porridge) provide long-lasting energy due to their low glycemic index. These whole grains are rich in dietary fiber and essential minerals like magnesium. Local honey and yak ghee are also great sources of high-quality fats for cold weather.", 3.5f, "Nutrition"),
        HealthArticle(5, "Ayurvedic Herbs in Nepalese Cooking", "Many common ingredients in Nepalese kitchens like Turmeric (Besar), Ginger (Aduwa), and Fenugreek (Methi) have powerful medicinal properties. Turmeric is a strong anti-inflammatory, ginger aids digestion and fights colds, and fenugreek helps regulate blood sugar levels. Integrating these intentionally into daily meals can enhance overall wellness.", 3f, "Wellness"),
        HealthArticle(6, "The Importance of Hydration in Nepal", "With varied altitudes and often dry climates, staying hydrated is crucial. Traditional drinks like 'Mohi' (Buttermilk) or 'Lassi' not only provide hydration but also offer probiotics and electrolytes. Avoiding excessive tea and coffee, which can be dehydrating, and focusing on fresh water and local herbal infusions is recommended for peak performance.", 2.5f, "Health"),
        HealthArticle(7, "Staying Active in Daily Life", "Physical activity doesn't always have to happen in the gym. Traditional Nepalese lifestyles, which often involve walking and manual labor, are naturally active. Replicating this by choosing to walk short distances or taking the stairs can significantly improve cardiovascular health. Aim for at least 30 minutes of moderate activity daily.", 3f, "Fitness"),
        HealthArticle(8, "Sleep and Himalayan Wellness", "Quality sleep is essential for recovery, especially after strenuous physical activity. Traditional Himalayan wellness practices emphasize natural sleep cycles. Keeping a cool room, avoiding heavy spices right before bed, and using herbal teas like chamomile or local 'Tulsi' can help improve sleep quality for better muscle repair.", 3f, "Wellness"),
        HealthArticle(9, "Modernizing Traditional Meals", "While traditional food is healthy, modern cooking often uses excessive oil and salt. By reducing oil and using steaming or grilling methods (like Sekuwa style), you can make traditional dishes even healthier. For example, opting for brown rice or millet instead of white rice can increase the fiber content of your Dal Bhat.", 4f, "Nutrition"),
        HealthArticle(10, "Spices for Metabolism", "Nepalese cuisine uses a variety of spices that can naturally boost metabolism. Black pepper (Marich), Cinnamon (Daalchini), and Cardamom (Sukmel) are known to enhance thermogenesis. Using these in tea or food can aid in weight management and improve insulin sensitivity.", 3f, "Fitness"),
        HealthArticle(11, "Importance of Local Seasonal Fruits", "Nepal is rich in seasonal fruits like Aiselu, Kafal, Guava, and Mango. These fruits are often more nutrient-dense than imported ones because they are consumed fresh. They are packed with Vitamin C and antioxidants that help fight oxidative stress and boost the immune system.", 3.5f, "Nutrition"),
        HealthArticle(12, "Warm-up Routine for Hilly Terrains", "Before embarking on a trek or a local hike, a proper dynamic warm-up is essential. Focus on joint rotations for ankles and knees. Simple movements like leg swings and torso twists can prevent injuries on uneven paths. Spending just 5 minutes on these can make a big difference.", 2.5f, "Fitness"),
        HealthArticle(13, "Mental Resilience and Yoga", "Yoga and meditation have deep roots in the Himalayan region. Practicing these can improve mental resilience and focus. Simple breathing exercises (Pranayama) can help in managing stress and even improve oxygen utilization at higher altitudes. A healthy mind is just as important as a healthy body.", 3f, "Wellness"),
        HealthArticle(14, "Protein Sources in Nepalese Diet", "While meat is a common protein source, many Nepalese foods provide plant-based protein. Soybeans (Bhatmas), Chickpeas (Chana), and various lentils (Dal) are staples. For vegetarians, Paneer and Chhurpi (local hard cheese) are excellent alternatives. Aiming for a mix of protein sources ensures a complete amino acid profile.", 3f, "Nutrition"),
        HealthArticle(15, "Setting Sustainable Goals", "In fitness, consistency is key. Set small, achievable goals like 'walking to the market instead of taking a bus' or 'adding one more vegetable to Dal Bhat'. Tracking these small wins builds momentum. Use this app's daily plan to stay accountable to yourself.", 3f, "Fitness"),
        HealthArticle(16, "The Benefits of Walking", "Walking is one of the most accessible forms of exercise. It improves cardiovascular health, strengthens bones, and burns calories. Aim for 10,000 steps daily. Walking after meals aids digestion and blood sugar control. It's low-impact, making it suitable for all fitness levels. Try walking meetings or phone calls to add steps throughout the day.", 2.5f, "Fitness"),
        HealthArticle(17, "Healthy Snacking Habits", "Smart snacking keeps energy levels stable throughout the day. Choose snacks with protein and fiber for sustained energy. Pre-portion snacks to avoid overeating. Keep healthy options visible and accessible. Good choices include nuts, fruits, yogurt, and veggie sticks. Avoid mindless snacking while watching TV. Plan snacks just like you plan meals.", 3f, "Nutrition"),
        HealthArticle(18, "Avoiding Common Workout Injuries", "Proper form is the best injury prevention. Start with lighter weights to master technique. Increase intensity gradually (10% rule). Listen to your body—pain is a warning sign. Include rest days in your routine. Warm up before and cool down after every session. Consider working with a trainer when learning new exercises.", 3.5f, "Fitness"),
        HealthArticle(19, "Building a Home Workout Routine", "You don't need a gym to get fit. Bodyweight exercises like push-ups, squats, and lunges are highly effective. Create a dedicated workout space at home. Follow a structured program rather than random exercises. Use household items as weights. Set a consistent schedule. Online fitness communities can provide motivation and accountability.", 3f, "Fitness"),
        HealthArticle(20, "The Power of Consistency", "Consistency trumps intensity in fitness. Showing up regularly, even for shorter workouts, builds lasting habits. Aim for progress, not perfection. Track your workouts to see improvement over time. Make exercise a non-negotiable part of your day. Start with just 15-20 minutes if time is limited. Remember that rest days are part of consistency too.", 2.5f, "Motivation"),
    )
}
