package br.com.jkavdev.fullcycle.admin.catalogo.domain.castmember;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.validation.Error;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.validation.ValidationHandler;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.validation.Validator;

public class CastMemberValidator extends Validator {

    public static final int NAME_MIN_LENGTH = 3;
    public static final int NAME_MAX_LENGTH = 255;

    private final CastMember aMember;

    public CastMemberValidator(final CastMember aMember, final ValidationHandler aHandler) {
        super(aHandler);
        this.aMember = aMember;
    }

    @Override
    public void validate() {
        checkNameConstraints();
        checkTypeConstraints();
    }

    private void checkNameConstraints() {
        final var name = this.aMember.getName();
        if (name == null) {
            this.validationHandler().append(new Error("'name' should not be null"));
            return;
        }

        if (name.isBlank()) {
            this.validationHandler().append(new Error("'name' should not be empty"));
            return;
        }

        final var nameLength = name.trim().length();
        if (nameLength < NAME_MIN_LENGTH || nameLength > NAME_MAX_LENGTH) {
            this.validationHandler().append(new Error("'name' must be between 3 and 255 characters"));
        }
    }

    private void checkTypeConstraints() {
        final var type = this.aMember.getType();
        if (type == null) {
            this.validationHandler().append(new Error("'type' should not be null"));
        }
    }


}
