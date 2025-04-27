package kz.tech.nuverse.controller.dictionary;

import kz.tech.nuverse.controller.dictionary.common.GenericDictionaryController;
import kz.tech.nuverse.model.dictionary.RoleDictionaryEntity;
import kz.tech.nuverse.model.dto.dictionary.base.BaseDictionaryDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dictionaries/user-roles")
public class UserRoleController extends GenericDictionaryController<RoleDictionaryEntity, BaseDictionaryDTO> {
}
