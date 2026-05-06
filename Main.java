public class Main {
    public static void main(String[] args) {
        String text = " java java python Java c C++ Ruby on rails kvkvkv vkvkvk";

        System.out.println("HASHSET");

        // длина слова
        WordSet set1 = new WordSet(Word.HashType.LENGTH);
        set1.addWordsFromText(text);
        set1.printAllWords();
        System.out.println("Размер: " + set1.size());
        System.out.println("Содержит 'java': " + set1.contains("java"));
        System.out.println();

        // количество гласных
        WordSet set2 = new WordSet(Word.HashType.VOWELS_COUNT);
        set2.addWordsFromText(text);
        set2.printAllWords();
        System.out.println("Размер: " + set2.size());
        System.out.println();

        // разница первой и последней буквы
        WordSet set3 = new WordSet(Word.HashType.FIRST_LAST_DIFF);
        set3.addWordsFromText(text);
        set3.printAllWords();
        System.out.println("Размер: " + set3.size());
        System.out.println();

        // сумма кодов букв
        /*
        // Расчет суммы кодов для каждого слова
 "java"   = 'j'(106) + 'a'(97) + 'v'(118) + 'a'(97)   = 418
 "python" = 'p'(112) + 'y'(121) + 't'(116) + 'h'(104) + 'o'(111) + 'n'(110) = 674
 "c++"    = 'c'(99)                                    = 99
 "ruby"   = 'r'(114) + 'u'(117) + 'b'(98) + 'y'(121)   = 450
 "on"     = 'o'(111) + 'n'(110)                        = 221
 "rails"  = 'r'(114) + 'a'(97) + 'i'(105) + 'l'(108) + 's'(115) = 539
 "kvkvkv" = 107 + 118 + 107 + 118 + 107 + 118          =  675
         */
        WordSet set4 = new WordSet(Word.HashType.SUM_CODES);
        set4.addWordsFromText(text);
        set4.printAllWords();
        System.out.println("Размер: " + set4.size());
        System.out.println("\n");

        System.out.println("TREESET");

        // сортировка по длине слова
        WordSet set5 = new WordSet(new ByLengthComparator());
        set5.addWordsFromText(text);
        set5.printAllWords();
        System.out.println("Размер: " + set5.size());
        System.out.println();

        // сортировка по первым трем символам
        WordSet set6 = new WordSet(new ByFirstThreeCharsComparator());
        set6.addWordsFromText(text);
        set6.printAllWords();
        System.out.println("Размер: " + set6.size());
        System.out.println();

        // сортировка по количеству уникальных символов
        WordSet set7 = new WordSet(new ByUniqueCharsCountComparator());
        set7.addWordsFromText(text);
        set7.printAllWords();
        System.out.println("Размер: " + set7.size());
        System.out.println();

        // сортировка в обратном алфавитном порядке
        WordSet set8 = new WordSet(new ReverseAlphabetComparator());
        set8.addWordsFromText(text);
        set8.printAllWords();
        System.out.println("Размер: " + set8.size());
        System.out.println("\n");



        System.out.println("COMPARABLE");

        // алфавитный порядок через Comparable
        System.out.println("Тест 1:");
        WordSet naturalSet = new WordSet(true);
        naturalSet.addWordsFromText(text);
        naturalSet.printAllWords();
        System.out.println("Размер: " + naturalSet.size());
        System.out.println("Содержит 'ruby': " + naturalSet.contains("ruby"));
        System.out.println();

        // Сравнение разных подходов на одном тексте
        System.out.println("Тест 2: Сравнение порядка сортировки на тексте: 'cat dog apple zebra banana'");
        String testText = "cat dog apple zebra banana";

        WordSet comparableSet = new WordSet(true);
        comparableSet.addWordsFromText(testText);
        System.out.print("Comparable (алфавитный): ");
        System.out.println(comparableSet.getAllWords());

        WordSet lengthSet = new WordSet(new ByLengthComparator());
        lengthSet.addWordsFromText(testText);
        System.out.print("Comparator (по длине): ");
        System.out.println(lengthSet.getAllWords());

        WordSet reverseSet = new WordSet(new ReverseAlphabetComparator());
        reverseSet.addWordsFromText(testText);
        System.out.print("Comparator (обратный алфавит): ");
        System.out.println(reverseSet.getAllWords());
        System.out.println();

        // Демонстрация работы compareTo при добавлении похожих слов
        System.out.println("Тест 3: ");
        WordSet comparableSet2 = new WordSet(true);
        comparableSet2.addWordsFromText("java javascript java python javascript");
        comparableSet2.printAllWords();
        System.out.println();

        // Проверка на регистронезависимость
        System.out.println("Тест 4: Проверка регистронезависимости");
        WordSet caseSet = new WordSet(true);
        caseSet.addWordsFromText("Java JAVA java PYTHON Python");
        caseSet.printAllWords();
        System.out.println();

        // Комбинация HashSet и TreeSet
        System.out.println("Тест 5: HashSet с хэшированием + TreeSet с Comparable");
        String complexText = "java python java c++ ruby javascript python";

        WordSet hashWordSet = new WordSet(Word.HashType.SUM_CODES);
        hashWordSet.addWordsFromText(complexText);
        System.out.println("HashSet (без порядка):");
        System.out.println(hashWordSet.getAllWords());

        WordSet treeWordSet = new WordSet(true);
        treeWordSet.addWordsFromText(complexText);
        System.out.println("TreeSet (алфавитный порядок через Comparable):");
        System.out.println(treeWordSet.getAllWords());
        System.out.println("Размер обоих множеств одинаковый: " +
                (hashWordSet.size() == treeWordSet.size() ? hashWordSet.size() : "не совпадает"));
        System.out.println();

        // операции удаления и добавления

        WordSet demoSet = new WordSet(new ByLengthComparator());
        demoSet.addWordsFromText("hello world java");
        System.out.println("Исходное множество:");
        demoSet.printAllWords();

        demoSet.remove("world");
        System.out.println("\nПосле удаления 'world':");
        demoSet.printAllWords();

        demoSet.addWordsFromText("programming");
        System.out.println("\nПосле добавления 'programming':");
        demoSet.printAllWords();

        demoSet.clear();
        System.out.println("\nПосле очистки, размер: " + demoSet.size());
    }
}