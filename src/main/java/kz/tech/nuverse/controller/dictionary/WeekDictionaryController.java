package kz.tech.nuverse.controller.dictionary;

import kz.tech.nuverse.controller.dictionary.common.GenericDictionaryController;
import kz.tech.nuverse.model.dictionary.WeekDictionaryEntity;
import kz.tech.nuverse.model.dto.dictionary.WeekDictionaryDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dictionaries/weeks")
public class WeekDictionaryController extends GenericDictionaryController<WeekDictionaryEntity, WeekDictionaryDTO> {
}
