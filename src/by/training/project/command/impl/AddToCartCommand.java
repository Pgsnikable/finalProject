package by.training.project.command.impl;

import by.training.project.command.Command;
import by.training.project.command.CommandResult;
import by.training.project.command.NavigationType;
import by.training.project.entity.Cart;
import by.training.project.entity.Drug;
import by.training.project.entity.DrugDosage;
import by.training.project.exception.ExceptionMessage;
import by.training.project.service.DrugService;
import by.training.project.exception.ServiceException;
import by.training.project.exception.CommandException;
import by.training.project.manager.PageManager;
import by.training.project.manager.PageMappingConstant;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Optional;

public class AddToCartCommand implements Command {
    private static final String SESSION_CART = "cart";
    private static final String DRUG_DOSAGE_ID = "drugDosageId";
    private static final String SELECTED_QUANTITY = "selectedQuantity";

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        String page = PageManager.getPageURI(PageMappingConstant.CART_PAGE_KEY);
        Cart cart = (Cart) request.getSession().getAttribute(SESSION_CART);
        Long drugDosageId = Long.valueOf(request.getParameter(DRUG_DOSAGE_ID));
        Integer selectedQuantity = Integer.valueOf(request.getParameter(SELECTED_QUANTITY));

        try {
            Optional<DrugDosage> optionalDrugDosage = DrugService.getInstance().findDrugDosageById(drugDosageId);
            if (optionalDrugDosage.isPresent()) {
                DrugDosage drugDosage = optionalDrugDosage.get();
                Optional<Drug> optionalDrug = DrugService.getInstance().findDrugById(drugDosage.getDrugId());
                if (optionalDrug.isPresent()) {
                    Drug drug = optionalDrug.get();
                    cart.setDrugDosage(drugDosage);
                    cart.setDrugName(drug.getName());
                    cart.setRecipe(drug.getRecipeNedeed());
                    cart.setQuantity(selectedQuantity);
                    cart.setTotalPrice(drug.getPrice().multiply(new BigDecimal(selectedQuantity)));
                }
            }
        } catch (ServiceException e) {
            throw new CommandException(ExceptionMessage.COMMAND_EXCEPTION_MESSAGE, e);
        }
        return new CommandResult(page, NavigationType.FORWARD);
    }
}