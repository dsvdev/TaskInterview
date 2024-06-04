import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author dsvdev
 * Уровень - Senior
 * Задача - Написать функцию, которая группирует произведения по исполнителю
 * Разбор - https://youtu.be/KpB4iIX5cGw
 */
public class GetMusicByAuthors {
    public static void main(String[] args) {
        List<Music> musicList = List.of(
            new Music("Кукла колдуна", "Король и шут"),
            new Music("Звезда по имени солнце", "Виктор Цой"),
            new Music("За деньги да", "Инстасамка"),
            new Music("Группа крови", "Виктор Цой"),
            new Music("Разбешавшись прыгну со скалы", "Король и шут")
        );

        /*
        Должно получиться
        Король и шут - [Кукла колдуна, Разбешавшись прыгну со скалы]
        Инстасамка - [За деньги да]
        Виктор Цой - [Группа крови, Звезда по имени солнце]
        */

        Map<String, List<String>> musicByAuthor = getMusicByAuthors(musicList);

        printMusicByAuthors(musicByAuthor);
    }

    /**
     * Хорошее решение с использованием Stream API
     */
    public static Map<String, List<String>> getMusicByAuthors(List<Music> musicList) {
        return musicList.stream()
                .collect(
                        Collectors.groupingBy(
                                Music::getArtist,
                                Collectors.mapping(
                                        Music::getTitle,
                                        Collectors.toList()
                                )
                        )
                );
    }

    /**
     * Нормальное решение с обходом коллекции
     */
    public static Map<String, List<String>> getMusicByAuthorsByMiddle(List<Music> musicList) {
        Map<String, List<String>> result = new HashMap<>();

        for (Music music : musicList) {
            String artist = music.getArtist();
            String title = music.getTitle();

            if(!result.containsKey(artist)) {
                result.put(artist, new ArrayList<>());
            }
            result.get(artist).add(title);
        }

        return result;
    }


    /**
     * Нормальное решение с обходом коллекции
     * Недостатки:
     * - использование цикла for с ненужной переменной
     * - дублирование кода в if-else
     */
    public static Map<String, List<String>> getMusicByAuthorsByJunior(List<Music> musicList) {
        Map<String, List<String>> result = new HashMap<>();

        for (int i = 0; i < musicList.size(); i++) {
            String artist = musicList.get(i).getArtist();
            String title = musicList.get(i).getTitle();

            if(result.containsKey(artist)) {
                result.get(artist).add(title);
            } else {
                result.put(artist, new ArrayList<>());
                result.get(artist).add(title);
            }
        }

        return result;
    }

    /**
     * Вспомогательная функция для красивого вывода результата на экран
     * */
    public static void printMusicByAuthors(Map<String, List<String>> musicByAuthors) {
        for (String author : musicByAuthors.keySet()) {
            System.out.println(author);
            List<String> musics = musicByAuthors.get(author);
            for (String music : musics) {
                System.out.println(" - " + music);
            }
        }
    }
}
