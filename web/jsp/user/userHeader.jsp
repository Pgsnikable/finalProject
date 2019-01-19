<%--
  Created by IntelliJ IDEA.
  User: Булочка
  Date: 07.01.2019
  Time: 23:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<header>
    <nav class="navbar navbar-dark navbar-expand-md navbar-light fixed-top bg-light shadow rounded">
        <div class="collapse navbar-collapse" id="navbarCollapse">
            <%@include file="/jsp/locale.jsp" %>
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active mr-auto">
                    <form class="form-inline mt-1 p-1" method="post"
                          action="${pageContext.request.contextPath}/mainController">
                        <fmt:message var="categories" key="main.categories.button"/>
                        <input type="hidden" name="command" value="all_categories">
                        <button class="btn btn-outline-primary" type="submit">
                            ${categories}
                        </button>
                    </form>
                </li>
                <li class="nav-item">
                    <form class="form-inline mt-1 p-1" method="post"
                          action="${pageContext.request.contextPath}/mainController">
                        <fmt:message var="drugs" key="main.drugs.button"/>
                        <input type="hidden" name="command" value="all_drugs">
                        <button class="btn btn-outline-primary" type="submit">
                            ${drugs}
                        </button>
                    </form>
                </li>
                <li class="nav-item">
                    <form class="form-inline mt-1 p-1" method="post"
                          action="${pageContext.request.contextPath}/jsp/user/cart.jsp">
                        <fmt:message var="userCart" key="main.cart"/>
                        <button class="btn btn-outline-primary" type="submit">
                            ${userCart}
                        </button>
                    </form>
                </li>
                <li class="nav-item">
                    <form class="form-inline mt-1 p-1" method="post"
                          action="${pageContext.request.contextPath}/mainController">
                        <input type="hidden" name="command" value="all_orders">
                        <fmt:message var="allOrders" key="main.orders"/>
                        <button class="btn btn-outline-primary" type="submit">
                            ${allOrders}
                        </button>
                    </form>
                </li>
                <li class="nav-item">
                    <form class="form-inline mt-1 p-1" method="post"
                          action="${pageContext.request.contextPath}/mainController">
                        <input type="hidden" name="command" value="all_recipes">
                        <fmt:message var="allRecipes" key="main.recipes"/>
                        <button class="btn btn-outline-primary" type="submit">
                            ${allRecipes}
                        </button>
                    </form>
                </li>
                <li class="nav-item">
                    <form class="form-inline mt-1 p-1" method="post"
                          action="${pageContext.request.contextPath}/mainController">
                        <input type="hidden" name="command" value="go_to_recipe_request">
                        <fmt:message var="recipeRequest" key="main.recipe.request"/>
                        <button class="btn btn-outline-primary" type="submit">
                            ${recipeRequest}
                        </button>
                    </form>
                </li>
                <li class="nav-item">
                    <form class="form-inline mt-1 p-1" method="post"
                          action="${pageContext.request.contextPath}/mainController">
                        <input type="hidden" name="command" value="all_recipe_request">
                        <fmt:message var="myRequests" key="main.request.my"/>
                        <button class="btn btn-outline-primary" type="submit">
                            ${myRequests}
                        </button>
                    </form>
                </li>
                <li class="nav-item">
                    <form class="form-inline mt-1 p-1" method="post"
                          action="${pageContext.request.contextPath}/jsp/user/updateData.jsp">
                        <fmt:message var="update" key="main.user.update"/>
                        <button class="btn btn-outline-primary" type="submit">
                            ${update}
                        </button>
                    </form>
                </li>
                <li class="nav-item">
                    <form class="form-inline mt-1 p-1" method="post"
                          action="${pageContext.request.contextPath}/jsp/user/main.jsp">
                        <fmt:message var="home" key="main.home"/>
                        <button class="btn btn-outline-primary" type="submit">
                            ${home}
                        </button>
                    </form>
                </li>
                <li class="nav-item">
                    <form name="userCabinet" class="form-inline mt-1 p-1" method="post"
                          action="${pageContext.request.contextPath}/jsp/user/cabinet.jsp">
                        <fmt:message var="cabinet" key="main.cabinet"/>
                        <button class="btn btn-outline-primary" type="submit">
                            ${cabinet}
                        </button>
                    </form>
                </li>

            </ul>
            <form class="form-inline mt-1" method="post"
                  action="${pageContext.request.contextPath}/mainController">
                <fmt:message var="logout" key="logout.button"/>
                <input type="hidden" name="command" value="logout">
                <button class="btn btn-warning my-2 my-sm-0" type="submit">
                    <span data-feather="log-out"></span>
                    ${logout}
                    <span class="sr-only">(current)</span>
                </button>
            </form>
        </div>
    </nav>
</header>

