/*
Name: Kinjal Amit Heda
PRN: 123B1F048
Date: 22/07/25

Movie Recommendation System Optimization
A popular OTT platform, StreamFlix, offers personalized recommendations by sorting movies
based on user preferences, such as IMDB rating, release year, or watch time popularity.
However, during peak hours, sorting large datasets slows down the system.
As a backend engineer, you must:
● Implement Quicksort to efficiently sort movies based on various user-selected
parameters.
● Handle large datasets containing of movies while maintaining fast response times
*/
import java.util.Scanner;

class Movie {
    String title;
    double imdbRating;
    int releaseYear;
    int watchCount;

    public Movie(String title, double imdbRating, int releaseYear, int watchCount) {
        this.title = title;
        this.imdbRating = imdbRating;
        this.releaseYear = releaseYear;
        this.watchCount = watchCount;
    }
}


public class Assignment2 {

    // QuickSort 
    public static void quickSort(Movie[] movies, int low, int high, String sortBy) {
        if (low < high) {
            int pivotIndex = partition(movies, low, high, sortBy);
            quickSort(movies, low, pivotIndex - 1, sortBy);
            quickSort(movies, pivotIndex + 1, high, sortBy);
        }
    }

    // Partition 
    private static int partition(Movie[] movies, int low, int high, String sortBy) {
        Movie pivot = movies[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            boolean swap = false;

            // Compare 
            if (sortBy.equals("rating") && movies[j].imdbRating <= pivot.imdbRating) swap = true;
            else if (sortBy.equals("year") && movies[j].releaseYear <= pivot.releaseYear) swap = true;
            else if (sortBy.equals("watch") && movies[j].watchCount <= pivot.watchCount) swap = true;

            if (swap) {
                i++;
                Movie temp = movies[i];
                movies[i] = movies[j];
                movies[j] = temp;
            }
        }

        // Place pivot at correct position
        Movie temp = movies[i + 1];
        movies[i + 1] = movies[high];
        movies[high] = temp;

        return i + 1;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Movie[] movies = {
            new Movie("Avengers", 8.5, 2012, 9500),
            new Movie("Inception", 8.8, 2010, 8700),
            new Movie("Titanic", 7.8, 1997, 12000),
            new Movie("Matrix", 8.7, 1999, 9800),
            new Movie("Interstellar", 8.6, 2014, 7300),
            new Movie("Joker", 8.4, 2019, 8200),
            new Movie("Avatar", 7.9, 2009, 15000),
            new Movie("Gladiator", 8.5, 2000, 6600),
            new Movie("Up", 8.2, 2009, 5400),
            new Movie("Coco", 8.4, 2017, 4700)
        };

        System.out.print("Sort movies by (rating/year/watch): ");
        String sortBy = sc.next().toLowerCase();

        if (!sortBy.equals("rating") && !sortBy.equals("year") && !sortBy.equals("watch")) {
            System.out.println("Invalid sort option!");
            sc.close();
            return;
        }

        long startTime = System.nanoTime();
        quickSort(movies, 0, movies.length - 1, sortBy);
        long endTime = System.nanoTime();

        System.out.println("\nMovies sorted by " + sortBy + ":");
        System.out.printf("%-15s %-8s %-6s %-6s%n", "Title", "Rating", "Year", "Watch");
        System.out.println("-------------------------------------------");
        for (Movie m : movies) {
            System.out.printf("%-15s %-8.1f %-6d %-6d%n",
                              m.title, m.imdbRating, m.releaseYear, m.watchCount);
        }

        double timeTaken = (endTime - startTime) / 1_000_000.0; // milliseconds
        System.out.printf("%nTime taken by QuickSort: %.3f ms%n", timeTaken);

        sc.close();
    }
}
