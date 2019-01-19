<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--
  Created by IntelliJ IDEA.
  User: Егор
  Date: 23.12.2018
  Time: 22:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Main</title>
    <link href="/resources/css/bootstrap.css" rel="stylesheet">
    <link href="/resources/css/admin.css" rel="stylesheet">
</head>
<body>
<%@include file="/jsp/admin/adminHeader.jsp" %>
<main role="main" class="col-md-10 ml-sm-auto col-lg-10 px-4">
    <div class="px-3 py-3 pt-md-5 pb-md-4 mx-auto text-center">
        <form method="post" action="${pageContext.request.contextPath}/mainController">
            <input type="hidden" name="command" value="add_drug">
            <div class="col-md-6 mb-3">
                <fmt:message var="inputTitle" key="input.login.title"/>

                <fmt:message var="addDrug" key="admin.add.drug.form.label"/>
                <label for="drugName">${addDrug}</label>
                <fmt:message var="drugNamePlaceholder" key="admin.add.drug.name.placeholder"/>
                <input type="text" class="form-control" id="drugName" name="drugName"
                       placeholder="${drugNamePlaceholder}" required pattern="^[A-Z][a-z\s]{1,127}$|^[А-Я][а-яёъ\s]{1,127}$"
                       title="${inputTitle}" oninvalid="setCustomValidity(loc())" oninput="setCustomValidity('')"/>

                <fmt:message var="addDosage" key="admin.add.drug.dosage.label"/>
                <label for="drugName">${addDosage}</label>
                <fmt:message var="drugDosagePlaceholder" key="admin.add.drug.dosage.placeholder"/>
                <input type="text" class="form-control" id="drugName" name="drugDosageName"
                       placeholder="${drugDosagePlaceholder}" required pattern="^[A-Z][a-z\s\D\d]{1,63}$|^[А-Я][а-яёъ\s\D\d]{1,63}$"
                       title="${inputTitle}" oninvalid="setCustomValidity(loc())" oninput="setCustomValidity('')"/>


                <fmt:message var="drugCompositionLabel" key="admin.add.drug.composition.label"/>
                <label for="drugComposition">${drugCompositionLabel}</label>
                <fmt:message var="drugCompositionPlaceholder" key="admin.add.drug.composition.placeholder"/>
                <textarea maxlength="255" class="form-control" id="drugComposition" name="drugComposition"
                       placeholder="${drugCompositionPlaceholder}" required title="${inputTitle}"
                          oninvalid="setCustomValidity(loc())" oninput="setCustomValidity('')"></textarea>

                <fmt:message var="drugIndicationsLabel" key="admin.add.drug.indications.form.label"/>
                <label for="drugComposition">${drugIndicationsLabel}</label>
                <fmt:message var="drugIndicationsPlaceholder" key="admin.add.drug.indications.placeholder"/>
                <textarea maxlength="255" class="form-control" id="drugComposition" name="drugIndications"
                          placeholder="${drugIndicationsPlaceholder}" required title="${inputTitle}"
                          oninvalid="setCustomValidity(loc())" oninput="setCustomValidity('')"></textarea>

                <fmt:message var="drugApplicationsLabel" key="admin.add.drug.applications.form.label"/>
                <label for="drugComposition">${drugApplicationsLabel}</label>
                <fmt:message var="drugApplicationsPlaceholder" key="admin.add.drug.applications.form.placeholder"/>
                <textarea maxlength="255" class="form-control" id="drugComposition" name="drugApplication"
                          placeholder="${drugApplicationsPlaceholder}" required title="${inputTitle}"
                          oninvalid="setCustomValidity(loc())" oninput="setCustomValidity('')"></textarea>

                <fmt:message var="drugContraLabel" key="admin.add.drug.contra.label"/>
                <label for="drugComposition">${drugContraLabel}</label>
                <fmt:message var="drugContraPlaceholder" key="admin.add.drug.contra.placeholder"/>
                <textarea maxlength="255" class="form-control" id="drugComposition" name="drugContra"
                          placeholder="${drugContraPlaceholder}" required title="${inputTitle}"
                          oninvalid="setCustomValidity(loc())" oninput="setCustomValidity('')"></textarea>
                <fmt:message var="recipeAvail" key="admin.add.drug.recipe"/>
                ${recipeAvail}<br>
                <fmt:message var="yes" key="drug.recipe.label.yes"/>
                <input type="radio" name="recipe" value="true" checked>${yes}

                <fmt:message var="no" key="drug.recipe.label.no"/>
                <input type="radio" name="recipe" value="true">${no}<br>

                <fmt:message var="selectLabel" key="admin.add.select.category"/>
                <label for="selectCategory">${selectLabel}</label>
                <select id="selectCategory" class="custom-select w-auto" name="drugCategoryId" required>
                    <c:forEach var="category" items="${requestScope.drugCategory}" varStatus="status">
                        <fmt:message var="categorySelect" key="admin.add.drug.category.label"/>
                        <option hidden value="">${categorySelect}</option>
                        <option ${status.count eq 1 ? "selected" : ""} value="${category.categoryId}">
                            ${category.name}
                        </option>
                    </c:forEach>
                </select><br>

                <fmt:message var="drugQuantity" key="admin.add.drug.quantity"/>
                <label for="quantity">${drugQuantity}</label>
                <fmt:message var="quantityPlaceholder" key="admin.add.drug.quantity.placeholder"/>
                <input placeholder="${quantityPlaceholder}" id="quantity" class="w-auto" type="number" min="0" step="1"
                       max="1000" name="selectedQuantity" value="0"><br>

                <fmt:message var="drugPrice" key="admin.add.drug.price.label"/>
                <label for="quantity">${drugPrice}</label>
                <fmt:message var="pricePlaceholder" key="admin.add.drug.price.placeholder"/>
                <input placeholder="${pricePlaceholder}" id="quantity" class="w-auto" type="number" min="0.01" step="0.01"
                       max="1000" name="drugPrice" value="0.01" required><br>

                <fmt:message var="addDrugButton" key="admin.add.drug.label"/>
                <button class="btn " type="submit">
                    <span data-feather="plus-circle"></span>
                    ${addDrugButton} <span class="sr-only">(current)</span>
                </button>
            </div>
        </form>
    </div>
</main>
<%@include file="/jsp/footer.jsp" %>
<script src="/resources/js/bootstrap.js"></script>
<script src="/resources/js/jquery-3.3.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"
        integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut"
        crossorigin="anonymous"></script>
<!-- Icons -->
<script src="/resources/js/feather.min.js"></script>
<script>
    feather.replace()
</script>
<script>
    function loc() {
        var lng = document.getElementById("language").value;
        if (lng === 'ru_RU'){
            return 'Неверный формат';
        } else {
            return 'Incorrect format';
        }
    }
</script>
</body>
</html>
