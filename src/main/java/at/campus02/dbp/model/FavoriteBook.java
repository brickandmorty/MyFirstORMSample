package at.campus02.dbp.model;


import jakarta.persistence.*;

@Entity
@Table(name = "favorite_books")
public class FavoriteBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Beziehung zum User
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Beziehung zum Book
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private int stars;

    // Getter und Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }

    public int getStars() { return stars; }
    public void setStars(int stars) { this.stars = stars; }

    @Override
    public String toString() {
        return "FavoriteBook{id=" + id + ", user=" + user.getFirstname() +
                ", book=" + book.getTitle() + ", stars=" + stars + "}";
    }
}
