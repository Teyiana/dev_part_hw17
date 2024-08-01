package org.example.spring;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MainApplication.class)
public class NoteServiceTest {
    @Autowired
    private NoteService noteService;

    @Test
    public void testListAll() {
        Note note = new Note();
        note.setId(1l);
        note.setTitle("T");
        note.setContent("C");
        noteService.addNotes(note);
        List<Note> listNote = noteService.findAllNotes();
        assertTrue(listNote.contains(note));
    }

    @Test
    public void testDeleteById() {
        Note note = new Note();
        note.setId(2L);
        note.setTitle("T");
        note.setContent("C");
        noteService.addNotes(note);
        noteService.deleteNoteById(note.getId());
        List<Note> listNote = noteService.findAllNotes();
        assertTrue(!listNote.contains(note));
    }

    @Test
    void testThatDeleteByIdWorksDoesNotCorrect() {
        Assertions.assertThrows(RuntimeException.class, () -> noteService.deleteNoteById(-1));
    }


    @Test
    public void testThatUpdateWorksCorrect() {
        Note note = new Note();
        note.setId(note.getId());
        note.setTitle("TITLE");
        note.setContent("CONTENT");
        noteService.addNotes(note);
        noteService.update(note);
        List<Note> listNote = noteService.findAllNotes();
        assertTrue(listNote.contains(note));
    }

    @Test
    void testThatUpdateWorksDoesNotCorrect() {
        Assertions.assertThrows(RuntimeException.class, () -> noteService.update(null));
    }


    @Test
    public void testThatGetByIdWorksCorrect() {
        Note note = new Note();
        note.setId(1L);
        note.setTitle("T");
        note.setContent("C");
        noteService.addNotes(note);
        noteService.getNoteById(note.getId());
        List<Note> listNote = noteService.findAllNotes();
        assertTrue(listNote.contains(note));
    }

    @Test
    void testThatGetByIdWorksDoesNotCorrect() {
        Assertions.assertThrows(RuntimeException.class, () -> noteService.getNoteById(-1));
    }
}
