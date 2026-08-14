package com.example.data

enum class Department(
    val nameAmharic: String,
    val description: String,
    val subjects: List<String>
) {
    NIBEB_TSELOT(
        nameAmharic = "ንባብና ጸሎት",
        description = "መሠረታዊ ንባብ፣ ዳዊት፣ ውዳሴ ማርያምና የዘወትር ጸሎት",
        subjects = listOf(
            "ፊደል (የሆሄያት ንባብ)",
            "መልእክተ ዮሐንስ (ሐዋርያ)",
            "የሐዲስ ኪዳን ወንጌል (ማቴዎስ/ማርቆስ/ሉቃስ/ዮሐንስ)",
            "መዝሙረ ዳዊት (ዳዊት ደጋሚ)",
            "ውዳሴ ማርያምና አንቀጸ ብርሃን",
            "የዘወትር ጸሎትና መልክአ ማርያም",
            "መልክአ ኢየሱስና ጸሎተ ሃይማኖት"
        )
    ),
    ZEMA(
        nameAmharic = "ዜማ (ድጓ)",
        description = "የቅዱስ ያሬድ ዜማ፣ ጾመ ድጓ፣ ዝማሬና መዋሥዕት",
        subjects = listOf(
            "መሠረታዊ ዜማ (ግዕዝ፣ ዕዝል፣ አራራይ)",
            "ጾመ ድጓ (የዐቢይ ጾም ዜማ)",
            "ታላቁ ድጓ (የበዓላትና የአዝማናት ድጓ)",
            "ዝማሬና መዋሥዕት",
            "ምዕራፍና ጸሎተ ነግህ"
        )
    ),
    AQUAQUAM(
        nameAmharic = "አቋቋም",
        description = "የማኅሌት አቋቋም፣ ጽናጽል፣ ከበሮና መቋሚያ",
        subjects = listOf(
            "የጽናጽልና የከበሮ መሠረታዊ አመታት",
            "ማኅሌተ ጽጌ አቋቋም",
            "የድጓና የጾመ ድጓ አቋቋም",
            "የወርኃ በዓላት ማኅሌት አቋቋም",
            "የመቋሚያና የዝማሜ ሥርዓት"
        )
    ),
    QENE(
        nameAmharic = "ቅኔ",
        description = "የግዕዝ ቅኔ (ጉባኤ ቃና፣ ዘአምላኪየ፣ ወርቅና ሰም)",
        subjects = listOf(
            "ጉባኤ ቃና",
            "ዘአምላኪየ",
            "ሚበዝሑ",
            "ዋዜማ",
            "ሥላሴ",
            "ዕጣነ ሞገር",
            "ሰበካ",
            "ክብር ይእቲ",
            "የግዕዝ ሰዋስውና የግሥ ጥናት"
        )
    ),
    KEDASE(
        nameAmharic = "ቅዳሴ",
        description = "የቤተ ክርስቲያን ሥርዓተ ቅዳሴ ለአበውና ለዲያቆናት",
        subjects = listOf(
            "ሥርዓተ ቤተ ክርስቲያንና የዲያቆናት አገልግሎት",
            "ሥርዓተ ቅዳሴ (የሠለስቱ ምዕት)",
            "ቅዳሴ ሐዋርያት",
            "ቅዳሴ እግዚእ",
            "ቅዳሴ ማርያም",
            "ዐሥራ አራቱ ቅዳሴያት",
            "የካህናትና የዲያቆናት ተራዳኢነት"
        )
    ),
    METSAHIFT(
        nameAmharic = "መጻሕፍት / ትርጓሜ",
        description = "የብሉይና ሐዲስ ኪዳን መጻሕፍት ትርጓሜ",
        subjects = listOf(
            "ሐዲስ ኪዳን ትርጓሜ (አራቱ ወንጌላት)",
            "መልእክታትና የሐዋርያት ሥራ ትርጓሜ",
            "ብሉይ ኪዳን ትርጓሜ (ኦሪትና መጻሕፍተ ነቢያት)",
            "መጻሕፍተ ሊቃውንት (ሃይማኖተ አበው)",
            "መጻሕፍተ መነኮሳት (ማር ይስሐቅ፣ ፊልክስዩስ፣ አረጋዊ መንፈሳዊ)",
            "ፍትሐ ነገሥት ትርጓሜ"
        )
    );

    companion object {
        fun fromString(value: String): Department {
            return entries.firstOrNull { it.name == value || it.nameAmharic == value } ?: NIBEB_TSELOT
        }
    }
}
