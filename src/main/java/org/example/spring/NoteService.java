package org.example.spring;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@RequiredArgsConstructor
@Service
public class NoteService {
    private final NoteRepository noteRepository;

    private final AtomicLong idGenerator = new AtomicLong();
    private static final Logger LOGGER = LoggerFactory.getLogger(NoteService.class);

    public List<Note> findAllNotes() {
        return noteRepository.findAll();
    }

    public Note addNotes(Note note) {
        long id = idGenerator.incrementAndGet();
        note.setId(id);
        return noteRepository.save(note);
    }

    public void deleteNoteById(long id) {
        if (noteRepository.findById(id).isPresent()) {
            noteRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Note not found, id: " + id);
        }
    }

    public void update(Note note) {
       Note note1 = noteRepository.findById(note.getId()).orElseThrow();
       if(note.getContent() != null){
           note1.setContent(note.getContent());
       }
       if (note.getTitle() != null){
           note1.setTitle(note.getTitle());
       }
       noteRepository.save(note1);
    }


    public Note getNoteById(long id) {
        Optional<Note> note = noteRepository.findById(id);
        return note.orElseThrow(() -> new IllegalArgumentException("Note not found, id: " + id));
    }



    @PostConstruct
    public void postConstruct() {
        LOGGER.info("Bean created: {}", this.getClass().getName());
        Note note = new Note();
        note.setTitle("Title");
        note.setContent("Content");
        Note note2 = new Note();
        note2.setTitle("nature");
        note2.setContent("autumn");
        noteRepository.save(note);
        noteRepository.save(note2);
    }

}
