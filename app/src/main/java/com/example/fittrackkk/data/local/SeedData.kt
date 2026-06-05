package com.example.fittrackkk.data.local

import com.example.fittrackkk.data.model.*

object SeedData {

    fun getDietDays(): List<DietDay> {
        val meals = listOf(
            arrayOf("Oatmeal with Banana & Honey", "350", "Grilled Chicken Salad", "450", "Greek Yogurt & Almonds", "200", "Salmon with Steamed Veggies", "500"),
            arrayOf("Scrambled Eggs & Toast", "400", "Quinoa Bowl with Chickpeas", "480", "Apple Slices & Peanut Butter", "220", "Chicken Stir-Fry with Rice", "550"),
            arrayOf("Smoothie Bowl (Berries & Granola)", "380", "Turkey Wrap with Avocado", "420", "Trail Mix", "250", "Pasta with Marinara Sauce", "520"),
            arrayOf("Pancakes with Fresh Fruit", "420", "Lentil Soup with Bread", "400", "Protein Bar", "210", "Grilled Fish with Potatoes", "480"),
            arrayOf("Avocado Toast with Egg", "390", "Caesar Salad with Shrimp", "440", "Hummus & Veggie Sticks", "180", "Beef Tacos with Salsa", "560"),
            arrayOf("French Toast with Berries", "410", "Tuna Sandwich", "430", "Mixed Nuts", "240", "Vegetable Curry with Rice", "500"),
            arrayOf("Yogurt Parfait with Granola", "340", "Chicken Caesar Wrap", "460", "Dark Chocolate & Almonds", "200", "Shrimp Pasta", "530"),
            arrayOf("Egg Muffins with Veggies", "360", "Mediterranean Bowl", "470", "Banana with Almond Butter", "230", "Grilled Chicken with Quinoa", "490"),
            arrayOf("Whole Grain Cereal with Milk", "320", "Falafel Wrap", "440", "Rice Cakes with Peanut Butter", "190", "Baked Cod with Asparagus", "460"),
            arrayOf("Chia Pudding with Mango", "350", "Black Bean Burrito", "500", "Cottage Cheese & Berries", "180", "Turkey Meatballs with Pasta", "540"),
            arrayOf("Banana Pancakes", "400", "Grilled Veggie Sandwich", "380", "Edamame", "160", "Lemon Herb Chicken", "480"),
            arrayOf("Overnight Oats with Berries", "360", "Chicken Noodle Soup", "420", "Celery & Peanut Butter", "170", "Teriyaki Salmon", "520"),
            arrayOf("Breakfast Burrito", "450", "Greek Salad with Feta", "380", "Fruit Smoothie", "200", "Pork Tenderloin with Veggies", "510"),
            arrayOf("Spinach & Cheese Omelette", "380", "BBQ Chicken Salad", "460", "Popcorn (Air-popped)", "150", "Fish Tacos", "490"),
            arrayOf("Granola with Yogurt", "340", "Mushroom Risotto", "480", "Dried Fruit Mix", "220", "Chicken Parmesan", "560"),
            arrayOf("Waffles with Strawberries", "430", "Caprese Panini", "420", "String Cheese & Crackers", "200", "Beef Stir-Fry", "530"),
            arrayOf("Peanut Butter Banana Smoothie", "380", "Sushi Bowl", "450", "Granola Bar", "190", "Baked Chicken Thighs", "500"),
            arrayOf("Veggie Omelette", "360", "Pho (Vietnamese Soup)", "440", "Yogurt with Honey", "180", "Lamb Chops with Salad", "550"),
            arrayOf("Acai Bowl", "390", "Club Sandwich", "470", "Roasted Chickpeas", "210", "Stuffed Bell Peppers", "480"),
            arrayOf("Cinnamon Raisin Toast", "320", "Minestrone Soup", "380", "Apple & Cheese Slices", "190", "Grilled Shrimp Skewers", "460"),
            arrayOf("Egg & Cheese Bagel", "440", "Cobb Salad", "460", "Mixed Berries", "130", "Chicken Alfredo", "570"),
            arrayOf("Protein Smoothie", "370", "Wrap with Hummus & Veggies", "390", "Trail Mix Bar", "220", "Pan-Seared Tilapia", "450"),
            arrayOf("Blueberry Muffin & Milk", "400", "Tom Yum Soup", "410", "Pear & Almond Butter", "200", "Spaghetti Bolognese", "540"),
            arrayOf("Smoked Salmon Bagel", "420", "Chicken Quesadilla", "480", "Veggie Chips", "180", "Roast Beef with Potatoes", "530"),
            arrayOf("Hash Browns & Eggs", "450", "Pasta Primavera", "440", "Fruit Salad", "160", "Garlic Butter Shrimp", "490"),
            arrayOf("Matcha Smoothie Bowl", "360", "BLT Sandwich", "430", "Protein Shake", "240", "Chicken Tikka Masala", "550"),
            arrayOf("Cornflakes with Banana", "330", "Burrito Bowl", "500", "Rice Crackers & Cheese", "200", "Baked Salmon with Rice", "510"),
            arrayOf("Mushroom & Cheese Omelette", "390", "Grilled Chicken Pita", "440", "Frozen Yogurt", "210", "Herb-Crusted Pork", "520"),
            arrayOf("Fruit & Nut Oatmeal", "380", "Asian Noodle Salad", "420", "Banana Chips", "190", "Seafood Paella", "560"),
            arrayOf("Celebration Breakfast Platter", "500", "Gourmet Burger & Salad", "520", "Chocolate Protein Balls", "250", "Filet Mignon with Veggies", "600")
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
        Recipe(1, "Oatmeal with Nut Butter", "Breakfast", "1 cup rolled oats\n2 tbsp almond butter\n1 banana\nHoney to taste\nCinnamon", "Cook oats with water or milk\nSlice banana on top\nDrizzle almond butter and honey\nSprinkle cinnamon", 380, 10, "oatmeal"),
        Recipe(2, "Avocado Toast", "Breakfast", "2 slices whole grain bread\n1 ripe avocado\n2 eggs\nSalt & pepper\nRed pepper flakes", "Toast bread until golden\nMash avocado with salt & pepper\nFry or poach eggs\nSpread avocado on toast and top with eggs", 420, 10, "avocado_toast"),
        Recipe(3, "Greek Yogurt Parfait", "Breakfast", "1 cup Greek yogurt\n1/2 cup granola\nMixed berries\nHoney", "Layer yogurt in a glass\nAdd granola layer\nTop with berries\nDrizzle honey", 320, 5, "parfait"),
        Recipe(4, "Banana Bread Toast", "Morning Snack", "2 slices banana bread\n1 tbsp cream cheese\nSliced banana\nCinnamon", "Toast banana bread slices\nSpread cream cheese\nTop with banana slices\nSprinkle cinnamon", 340, 5, "banana_bread"),
        Recipe(5, "Protein Smoothie", "Morning Snack", "1 scoop protein powder\n1 banana\n1 cup almond milk\n1 tbsp peanut butter\nIce cubes", "Add all ingredients to blender\nBlend until smooth\nPour and serve immediately", 350, 5, "smoothie"),
        Recipe(6, "Grilled Chicken Salad", "Lunch", "200g chicken breast\nMixed greens\nCherry tomatoes\nCucumber\nOlive oil vinaigrette", "Season and grill chicken\nChop veggies and arrange on plate\nSlice chicken and place on top\nDrizzle with vinaigrette", 420, 20, "chicken_salad"),
        Recipe(7, "Quinoa Power Bowl", "Lunch", "1 cup cooked quinoa\nBlack beans\nCorn\nAvocado\nLime juice\nCilantro", "Cook quinoa and let cool\nRinse and drain black beans\nDice avocado\nCombine all with lime juice and cilantro", 460, 15, "quinoa_bowl"),
        Recipe(8, "Turkey Wrap", "Lunch", "Whole wheat tortilla\n150g turkey breast\nLettuce\nTomato\nMustard", "Lay tortilla flat\nLayer turkey, lettuce, tomato\nAdd mustard\nRoll tightly and slice", 380, 10, "turkey_wrap"),
        Recipe(9, "Energy Balls", "Afternoon Snack", "1 cup oats\n1/2 cup peanut butter\n1/3 cup honey\nChocolate chips\nFlaxseed", "Mix all ingredients in bowl\nRoll into small balls\nRefrigerate for 30 min\nStore in airtight container", 180, 15, "energy_balls"),
        Recipe(10, "Hummus & Veggies", "Afternoon Snack", "1 cup hummus\nCarrot sticks\nCelery sticks\nBell pepper strips\nCucumber slices", "Prepare vegetables by washing and cutting\nScoop hummus into bowl\nArrange veggies around hummus\nServe immediately", 200, 10, "hummus_veggies"),
        Recipe(11, "Salmon with Veggies", "Dinner", "200g salmon fillet\nBroccoli\nSweet potato\nOlive oil\nLemon\nGarlic", "Preheat oven to 400°F\nSeason salmon with garlic and lemon\nToss veggies in olive oil\nBake everything for 20 minutes", 520, 30, "salmon"),
        Recipe(12, "Chicken Stir-Fry", "Dinner", "200g chicken breast\nBell peppers\nSnap peas\nSoy sauce\nGinger\nRice", "Cook rice separately\nStir-fry chicken until golden\nAdd vegetables and sauce\nServe over rice", 550, 25, "stir_fry"),
    )

    fun getHealthArticles(): List<HealthArticle> = listOf(
        HealthArticle(1, "How to Increase Appetite Naturally", "Eating small, frequent meals throughout the day can help boost your appetite. Start with nutrient-dense snacks like nuts, seeds, and dried fruits. Stay hydrated but avoid drinking too much water before meals. Exercise regularly to stimulate hunger hormones. Consider adding spices like ginger and black pepper to your meals, as they can help stimulate digestion and appetite. Getting enough sleep is also crucial, as poor sleep can suppress appetite-regulating hormones.", 4f, "Nutrition"),
        HealthArticle(2, "The Role of Healthy Fats", "Healthy fats are essential for hormone production, brain function, and nutrient absorption. Include sources like avocados, olive oil, nuts, seeds, and fatty fish in your diet. Omega-3 fatty acids found in salmon and walnuts help reduce inflammation. Aim for 25-35% of your daily calories from healthy fats. Avoid trans fats found in processed foods. Coconut oil and ghee are good options for cooking at high temperatures.", 3f, "Nutrition"),
        HealthArticle(3, "Understanding Metabolism", "Metabolism refers to all chemical processes in your body that convert food to energy. Factors affecting metabolism include age, muscle mass, body size, and genetics. Building lean muscle through resistance training can boost your metabolic rate. Eating enough protein helps maintain muscle mass and has a higher thermic effect. Staying active throughout the day, not just during workouts, keeps your metabolism elevated.", 3f, "Health"),
        HealthArticle(4, "High Calorie Foods for Healthy Weight Gain", "If you're trying to gain weight healthily, focus on calorie-dense, nutrient-rich foods. Nuts and nut butters provide healthy fats and protein. Whole grain bread, pasta, and rice are excellent carb sources. Add cheese, olive oil, and avocado to meals for extra calories. Smoothies with protein powder, banana, and peanut butter are easy high-calorie options. Dried fruits are calorie-dense snacks. Eat larger portions and add healthy toppings to your meals.", 3.5f, "Nutrition"),
        HealthArticle(5, "Stress Management and Fitness", "Chronic stress can sabotage your fitness goals by increasing cortisol levels, which promotes fat storage and muscle breakdown. Practice stress-reduction techniques like deep breathing, meditation, or yoga. Regular exercise itself is a powerful stress reliever. Ensure adequate sleep of 7-9 hours per night. Social connections and hobbies can also help manage stress levels.", 3f, "Wellness"),
        HealthArticle(6, "The Importance of Hydration", "Water is essential for every bodily function. Aim for 8-10 glasses daily, more if you exercise. Dehydration can reduce exercise performance by up to 25%. Water helps transport nutrients, regulate temperature, and remove waste. Signs of dehydration include dark urine, fatigue, and headaches. Fruits and vegetables with high water content can contribute to your daily intake.", 2.5f, "Health"),
        HealthArticle(7, "Benefits of Strength Training", "Strength training builds muscle, increases bone density, and boosts metabolism. It improves posture, reduces injury risk, and enhances daily functional movements. Even 2-3 sessions per week can show significant results. Start with bodyweight exercises before progressing to weights. Progressive overload is key to continued improvement. Don't skip rest days—muscles grow during recovery.", 3f, "Fitness"),
        HealthArticle(8, "Sleep and Recovery", "Quality sleep is when your body repairs and builds muscle. Aim for 7-9 hours per night. Poor sleep increases hunger hormones and reduces willpower. Create a bedtime routine: dim lights, avoid screens, and keep your room cool. Avoid caffeine after 2 PM. Post-workout nutrition before bed can enhance overnight muscle recovery.", 3f, "Wellness"),
        HealthArticle(9, "Meal Prep for Beginners", "Meal prepping saves time and helps you stick to your nutrition goals. Start by planning 3-4 meals for the week. Cook proteins, grains, and veggies in bulk. Invest in quality containers. Prep on Sunday for the week ahead. Keep it simple with versatile ingredients. Label containers with dates. Frozen prepped meals can last 2-3 months.", 4f, "Nutrition"),
        HealthArticle(10, "Stretching and Flexibility", "Regular stretching improves range of motion, reduces injury risk, and aids recovery. Dynamic stretching before workouts warms up muscles. Static stretching after workouts helps cool down. Hold each stretch for 15-30 seconds without bouncing. Focus on major muscle groups: hamstrings, quads, hip flexors, and shoulders. Yoga is an excellent way to improve flexibility.", 3f, "Fitness"),
        HealthArticle(11, "Understanding Macronutrients", "Macronutrients—protein, carbs, and fats—are the building blocks of nutrition. Protein (4 cal/g) builds and repairs tissue. Carbs (4 cal/g) are the body's primary energy source. Fats (9 cal/g) support hormones and brain function. A balanced diet typically includes 40-50% carbs, 25-35% protein, and 20-30% fats. Adjust ratios based on your fitness goals.", 3.5f, "Nutrition"),
        HealthArticle(12, "Warming Up Properly", "A proper warm-up increases blood flow, raises body temperature, and prepares muscles for exercise. Spend 5-10 minutes on light cardio like walking or jogging. Add dynamic movements that mimic your workout. Gradually increase intensity. Never skip warm-ups—cold muscles are more prone to injury. Include joint rotations for mobility.", 2.5f, "Fitness"),
        HealthArticle(13, "Mental Health and Exercise", "Exercise releases endorphins, serotonin, and dopamine—natural mood boosters. Regular physical activity reduces symptoms of anxiety and depression. Even a 30-minute walk can improve mood. Group fitness activities provide social benefits. Setting and achieving fitness goals builds self-confidence. Mind-body exercises like yoga combine physical and mental benefits.", 3f, "Wellness"),
        HealthArticle(14, "Post-Workout Nutrition", "What you eat after exercise significantly impacts recovery. Consume protein and carbs within 30-60 minutes post-workout. Good options include protein shakes, chicken with rice, or yogurt with fruit. Protein helps repair muscle fibers while carbs replenish glycogen stores. Hydrate well to replace fluids lost through sweat. Avoid high-fat meals immediately after exercise as they slow digestion.", 3f, "Nutrition"),
        HealthArticle(15, "Setting Realistic Fitness Goals", "SMART goals (Specific, Measurable, Achievable, Relevant, Time-bound) lead to better results. Start with small, achievable targets. Track your progress regularly. Celebrate small wins along the way. Adjust goals as you progress. Focus on consistency over perfection. Having a workout buddy or coach increases accountability.", 3f, "Fitness"),
        HealthArticle(16, "The Benefits of Walking", "Walking is one of the most accessible forms of exercise. It improves cardiovascular health, strengthens bones, and burns calories. Aim for 10,000 steps daily. Walking after meals aids digestion and blood sugar control. It's low-impact, making it suitable for all fitness levels. Try walking meetings or phone calls to add steps throughout the day.", 2.5f, "Fitness"),
        HealthArticle(17, "Healthy Snacking Habits", "Smart snacking keeps energy levels stable throughout the day. Choose snacks with protein and fiber for sustained energy. Pre-portion snacks to avoid overeating. Keep healthy options visible and accessible. Good choices include nuts, fruits, yogurt, and veggie sticks. Avoid mindless snacking while watching TV. Plan snacks just like you plan meals.", 3f, "Nutrition"),
        HealthArticle(18, "Avoiding Common Workout Injuries", "Proper form is the best injury prevention. Start with lighter weights to master technique. Increase intensity gradually (10% rule). Listen to your body—pain is a warning sign. Include rest days in your routine. Warm up before and cool down after every session. Consider working with a trainer when learning new exercises.", 3.5f, "Fitness"),
        HealthArticle(19, "Building a Home Workout Routine", "You don't need a gym to get fit. Bodyweight exercises like push-ups, squats, and lunges are highly effective. Create a dedicated workout space at home. Follow a structured program rather than random exercises. Use household items as weights. Set a consistent schedule. Online fitness communities can provide motivation and accountability.", 3f, "Fitness"),
        HealthArticle(20, "The Power of Consistency", "Consistency trumps intensity in fitness. Showing up regularly, even for shorter workouts, builds lasting habits. Aim for progress, not perfection. Track your workouts to see improvement over time. Make exercise a non-negotiable part of your day. Start with just 15-20 minutes if time is limited. Remember that rest days are part of consistency too.", 2.5f, "Motivation"),
    )
}
