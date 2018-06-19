package dao;

import models.Artist;
import models.Note;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oNoteDao implements NoteDao {

    private final Sql2o sql2o;

    public Sql2oNoteDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public List<Note> getAllByReleaseId(int id) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM notes WHERE releaseId = :id")
                    .addParameter("id", id)
                    .executeAndFetch(Note.class);
        }
    }

    @Override
    public void add(Note note) {
        String sql = "INSERT INTO notes (content, postedAt, releaseId) VALUES (:content, :postedAt, :releaseId)";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql, true)
                    .bind(note)
                    .executeUpdate()
                    .getKey();
            note.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }

    }

    @Override
    public void update(int id, String content) {
        String sql = "UPDATE notes SET (content) = (:content) WHERE id = :id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("content", content)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void clearAllNotesByRelesaseId() {

    }
}
