package org.example.spring;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/note")
public class NoteController {
private final NoteService noteService;


    @GetMapping("/list")
    public ModelAndView list() {
        ModelAndView model = new ModelAndView("index");
        List<Note> all = noteService.findAllNotes();
        model.addObject("action", "findAllNotes");
        model.addObject("notes", all);
        return model;
    }

    @PostMapping("/delete")
    public String delete(@RequestParam long id) {
        noteService.deleteNoteById(id);
        return "redirect:/note/list";
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/edit")
    public ModelAndView edit(@RequestParam Long id) {
        ModelAndView model = new ModelAndView("index");
        Note note = noteService.getNoteById(id);
        model.addObject("action", "edit");
        model.addObject("note", note);
        return model;
    }


    @PostMapping("/edit")
    public String edit(@RequestParam Long id,
                       @RequestParam String title,
                       @RequestParam String content) {
        Note note = new Note();
        note.setId(id);
        note.setTitle(title);
        note.setContent(content);
        noteService.update(note);
        return "redirect:/note/list";
    }
}
