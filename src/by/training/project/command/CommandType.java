package by.training.project.command;

import by.training.project.command.impl.*;

public enum CommandType {
    LOGIN(new LoginCommand()),
    REGISTRATION(new RegistrationCommand()),
    LOGOUT(new LogoutCommand()),
    ALL_CATEGORIES (new AllCategoriesCommand()),
    CHANGE_LANGUAGE(new ChangeLanguageCommand()),
    FIND_DRUG_BY_CATEGORY(new FindDrugByCategoryCommand()),
    ADD_TO_CART(new AddToCartCommand()),
    ALL_DRUGS(new AllDrugsCommand()),
    DO_ORDER(new DoOrderCommand()),
    ALL_ORDERS(new AllOrdersCommand()),
    DECLINE_ORDER(new DeclineOrderCommand()),
    GO_TO_CHANGE_ORDER(new GoToChangeOrderCommand()),
    CHANGE_ORDER(new ChangeOrderCommand()),
    ALL_RECIPES(new AllRecipesCommand()),
    GO_TO_RECIPE_REQUEST(new GoToRecipeRequestCommand()),
    SEND_REQUEST(new SendRequestCommand()),
    ALL_RECIPE_REQUEST(new AllRecipeRequestCommand()),
    DELETE_REQUEST(new DeleteRecipeRequestCommand()),
    ADD_BALANCE(new AddBalanceCommand()),
    UPDATE_USER(new UpdateUserCommand()),
    SHOW_RECIPE_REQUEST(new ShowRecipeRequestCommand()),
    CHANGE_REQUEST(new ChangeRequestCommand()),
    SHOW_ALL_ORDERS(new ShowAllOrdersCommand()),
    CHANGE_ORDER_STATUS(new ChangeOrderStatusCommand()),
    ADD_CATEGORY(new AddCategoryCommand()),
    GO_TO_ADD_DRUG(new GoToAddDrugCommand()),
    ADD_DRUG(new AddDrugCommand()),
    GO_TO_ADD_DOSAGE(new GoToAddDosageCommand()),
    ADD_DOSAGE(new AddDosageCommand()),
    ALL_USERS(new AllUsersCommand()),
    DELETE_USER(new DeleteUserCommand()),
    GO_TO_ADD_USER(new GoToAddUserCommand()),
    ADD_USER(new AddUserCommand()),
    GO_TO_DELETE_DRUG(new GoToDeleteDrugCommand()),
    DELETE_DRUG(new DeleteDrugCommand()),
    GO_TO_DELETE_CATEGORY(new GoToDeleteCategoryCommand()),
    DELETE_CATEGORY(new DeleteCategoryCommand()),
    GO_TO_ALL_DRUGS(new GoToAllDrugsCommand());

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand(){
        return command;
    }
}
