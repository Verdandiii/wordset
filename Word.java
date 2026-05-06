//Создать класс для работы с множеством слов, содержащихся в некотором тексте.
// В простейшем случае - символьной строке.
//
// 1. Для хранения множества использовать HashSet.
// Реализовать 2 различныx варианта функции расстановки hashCode().
// Например, функция может считать кодом длину слова, или количество гласных букв,
// или разницу между кодами первой и последней буквы, сумму кодов всех букв и т.д.
// Следует учесть обязательное согласование между hashCode() и equals().
//
// 2.  Для хранения множества использовать TreeSet.
// Использовать разные варианты реализации интерфейсов Comparable и Comparator
// для получения разной упорядоченности множества. Например, упорядочить по количеству символов,
// по первым трем символам, по количеству совпадающих символов и т.д.

import java.util.*;

class Word implements Comparable<Word> {
    private String word;
    private HashType hashType;

    public enum HashType {
        LENGTH,
        VOWELS_COUNT,
        FIRST_LAST_DIFF,
        SUM_CODES
    }

    public Word(String word, HashType hashType) {
        this.word = word.toLowerCase();
        this.hashType = hashType;
    }

    public String getWord() {
        return word;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Word other = (Word) obj;
        return word.equals(other.word);
    }

    @Override
    public int hashCode() {
        switch (hashType) {
            case LENGTH:
                return word.length();
            case VOWELS_COUNT:
                return countVowels();
            case FIRST_LAST_DIFF:
                if (word.isEmpty()) return 0;
                return word.charAt(0) - word.charAt(word.length() - 1);
            case SUM_CODES:
                int sum = 0;
                for (char c : word.toCharArray()) {
                    sum += c;
                }
                return sum;
            default:
                return word.hashCode();
        }
    }

    // Реализация Comparable - естественный порядок (по алфавиту)
    @Override
    public int compareTo(Word other) {
        // Естественный порядок: лексикографическое сравнение
        return this.word.compareTo(other.word);
    }

    private int countVowels() {
        int count = 0;
        String vowels = "аеёиоуыэюяaeiou";
        for (char c : word.toCharArray()) {
            if (vowels.indexOf(c) != -1) {
                count++;
            }
        }
        return count;
    }

    @Override
    public String toString() {
        return word;
    }
}

// Компараторы для различных способов сортировки
class ByLengthComparator implements Comparator<Word> {
    @Override
    public int compare(Word w1, Word w2) {
        int lenCompare = Integer.compare(w1.getWord().length(), w2.getWord().length());
        if (lenCompare != 0) return lenCompare;
        // При равенстве длины используем естественный порядок (алфавитный)
        return w1.compareTo(w2);
    }
}

class ByFirstThreeCharsComparator implements Comparator<Word> {
    @Override
    public int compare(Word w1, Word w2) {
        String s1 = w1.getWord().length() >= 3 ? w1.getWord().substring(0, 3) : w1.getWord();
        String s2 = w2.getWord().length() >= 3 ? w2.getWord().substring(0, 3) : w2.getWord();
        int compare = s1.compareTo(s2);
        if (compare != 0) return compare;
        // При равенстве первых трех символов используем естественный порядок
        return w1.compareTo(w2);
    }
}

class ByUniqueCharsCountComparator implements Comparator<Word> {
    @Override
    public int compare(Word w1, Word w2) {
        int count1 = (int) w1.getWord().chars().distinct().count();
        int count2 = (int) w2.getWord().chars().distinct().count();
        int compare = Integer.compare(count1, count2);
        if (compare != 0) return compare;
        // При равенстве количества уникальных символов используем естественный порядок
        return w1.compareTo(w2);
    }
}

class ReverseAlphabetComparator implements Comparator<Word> {
    @Override
    public int compare(Word w1, Word w2) {
        // Обратный алфавитный порядок
        return w2.compareTo(w1);
    }
}

// Новый компаратор, демонстрирующий комбинацию с Comparable
class ByVowelCountComparator implements Comparator<Word> {
    @Override
    public int compare(Word w1, Word w2) {
        int vowelCount1 = countVowelsInWord(w1.getWord());
        int vowelCount2 = countVowelsInWord(w2.getWord());
        int compare = Integer.compare(vowelCount1, vowelCount2);
        if (compare != 0) return compare;
        // При равенстве используем естественный порядок
        return w1.compareTo(w2);
    }

    private int countVowelsInWord(String word) {
        int count = 0;
        String vowels = "аеёиоуыэюяaeiou";
        for (char c : word.toCharArray()) {
            if (vowels.indexOf(c) != -1) {
                count++;
            }
        }
        return count;
    }
}

class WordSet {
    private Set<Word> hashSet;
    private Set<Word> treeSet;
    private Word.HashType currentHashType;
    private Comparator<Word> currentComparator;

    // Конструктор для HashSet
    public WordSet(Word.HashType hashType) {
        this.currentHashType = hashType;
        this.hashSet = new HashSet<>();
        this.treeSet = null;
    }

    // Конструктор для TreeSet с comparator
    public WordSet(Comparator<Word> comparator) {
        this.currentComparator = comparator;
        this.treeSet = new TreeSet<>(comparator);
        this.hashSet = null;
    }

    // конструктор для TreeSet с comparable
    public WordSet(boolean useNaturalOrder) {
        if (useNaturalOrder) {
            // Используем естественный порядок, определенный в compareTo()
            this.treeSet = new TreeSet<>();
            this.currentComparator = null;
            this.hashSet = null;
        } else {
            throw new IllegalArgumentException("Use other constructor");
        }
    }

    public void addWordsFromText(String text) {
        if (text == null || text.trim().isEmpty()) return;

        String[] words = text.toLowerCase().split("[^а-яa-z0-9]+");

        for (String word : words) {
            if (!word.isEmpty()) {
                if (hashSet != null) {
                    hashSet.add(new Word(word, currentHashType));
                } else if (treeSet != null) {
                    treeSet.add(new Word(word, currentHashType != null ? currentHashType : Word.HashType.LENGTH));
                }
            }
        }
    }

    public boolean contains(String word) {
        Word searchWord = new Word(word, currentHashType != null ? currentHashType : Word.HashType.LENGTH);
        if (hashSet != null) {
            return hashSet.contains(searchWord);
        } else {
            return treeSet.contains(searchWord);
        }
    }

    public boolean remove(String word) {
        Word removeWord = new Word(word, currentHashType != null ? currentHashType : Word.HashType.LENGTH);
        if (hashSet != null) {
            return hashSet.remove(removeWord);
        } else {
            return treeSet.remove(removeWord);
        }
    }

    public int size() {
        if (hashSet != null) {
            return hashSet.size();
        } else {
            return treeSet.size();
        }
    }

    public void printAllWords() {
        if (hashSet != null) {
            System.out.println("HashSet (" + getHashTypeName(currentHashType) + "):");
            for (Word word : hashSet) {
                System.out.println("  " + word + " (hashCode: " + word.hashCode() + ")");
            }
        } else if (treeSet != null) {
            String orderType;
            if (currentComparator != null) {
                orderType = getComparatorName(currentComparator);
            } else {
                orderType = "порядок алфавитный через Comparable";
            }
            System.out.println("TreeSet (" + orderType + "):");
            for (Word word : treeSet) {
                System.out.println("  " + word);
            }
        }
    }

    public List<String> getAllWords() {
        List<String> result = new ArrayList<>();
        if (hashSet != null) {
            for (Word word : hashSet) {
                result.add(word.getWord());
            }
        } else if (treeSet != null) {
            for (Word word : treeSet) {
                result.add(word.getWord());
            }
        }
        return result;
    }

    private String getHashTypeName(Word.HashType type) {
        switch (type) {
            case LENGTH: return "по длине слова";
            case VOWELS_COUNT: return "по количеству гласных";
            case FIRST_LAST_DIFF: return "по разнице первой и последней буквы";
            case SUM_CODES: return "по сумме кодов букв";
            default: return "стандартный";
        }
    }

    private String getComparatorName(Comparator<Word> comparator) {
        if (comparator instanceof ByLengthComparator) return "по длине слова (Comparator)";
        if (comparator instanceof ByFirstThreeCharsComparator) return "по первым трем символам (Comparator)";
        if (comparator instanceof ByUniqueCharsCountComparator) return "по количеству уникальных символов (Comparator)";
        if (comparator instanceof ReverseAlphabetComparator) return "в обратном алфавитном порядке (Comparator)";
        if (comparator instanceof ByVowelCountComparator) return "по количеству гласных (Comparator)";
        return "стандартный (Comparator)";
    }

    public void clear() {
        if (hashSet != null) {
            hashSet.clear();
        } else if (treeSet != null) {
            treeSet.clear();
        }
    }
}
