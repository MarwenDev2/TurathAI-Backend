package pi.turathai.turathaibackend.Services;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class BadWordService {

    private static final List<String> BAD_WORDS = Arrays.asList(
            // English
            "fuck", "fucks", "fucking", "fucked", "f u c k", "f*ck", "f**k",
            "shit", "shits", "shitty", "sh!t", "s**t",
            "asshole", "ass", "a$$", "a s s", "a55",
            "bitch", "bitches", "b!tch", "b1tch", "biatch",
            "bastard", "dick", "d1ck", "d!ck", "cock", "c0ck", "cawk",
            "pussy", "pus5y", "pussie", "cunt", "cum", "jizz",
            "whore", "w h o r e", "slut", "s l u t",
            "motherfucker", "mother fucker", "mf", "m f", "mofo",
            "damn", "dammit", "goddamn", "hell", "crap",
            "screw you", "eat shit", "suck it",

            // French
            "merde", "putain", "enculé", "encule", "connard", "con", "salope",
            "nique", "nique ta mère", "ntm", "bite", "chatte", "pétasse", "trouduc",
            "foutre", "batard", "enfoiré", "bouffon", "gouine", "pd", "tafiole", "sac à foutre",

            // Arabic transliterations
            "zebi", "kos", "kess", "9a7ba", "qa7ba", "3ayr", "3ir", "nik", "nikmok", "walak",
            "bn k", "klb", "sharmouta", "sharmout", "kos ommak", "ommak", "ya hmar", "ya kalb",
            "kalb", "ya 3ayr", "3ayr", "ya 7mar", "7mar", "ya 3ir", "3ir", "ya zebi", "zebi",

            // Variants & leetspeak
            "f*ucking", "s!ut", "d@mn", "sh!thead", "c*nt", "bi@tch", "douche", "douchebag",
            "twat", "wanker", "prick", "arse", "bollocks", "bugger", "jackass",

            "insulte", "abus", "violence", "fuck", "fucker", "raciste", "hate", "nazi","bonjour",
            "bitch", "asshole", "idiot", "stupid", "puta", "mrd", "schwein", "cochon", "putain",
            "chienne", "douchebag", "bastardo", "cazzo", "mierda", "con", "fils de pute"
    );

    public boolean containsBadWords(String text) {
        if (text == null) {
            return false;
        }

        String lowerText = text.toLowerCase();
        return BAD_WORDS.stream().anyMatch(lowerText::contains);
    }
}
