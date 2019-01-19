<%--
  Created by IntelliJ IDEA.
  User: Булочка
  Date: 07.01.2019
  Time: 23:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container-fluid">
    <div class="row">
        <nav class="col-md-2 d-none d-md-block bg-light sidebar">
            <div class="sidebar-sticky">
                <ul class="nav flex-column">
                    <li class="nav-item">
                        <%@include file="/jsp/locale.jsp" %>
                    </li>
                    <li class="nav-item">
                        <form class="margin-bottom: 0px" method="post"
                              action="${pageContext.request.contextPath}/jsp/admin/main.jsp">
                            <fmt:message var="home" key="main.home"/>
                            <button class="btn btn-link nav-link" type="submit">
                                <span data-feather="home"></span>
                                ${home} <span class="sr-only">(current)</span>
                            </button>
                        </form>
                    </li>
                    <li class="nav-item">
                        <form style="margin-bottom: 0px" method="post"
                              action="${pageContext.request.contextPath}/mainController">
                            <fmt:message var="logout" key="logout.button"/>
                            <input type="hidden" name="command" value="logout">
                            <button class="btn btn-link nav-link" type="submit">
                                <span data-feather="log-out"></span>
                                ${logout} <span class="sr-only">(current)</span>
                            </button>
                        </form>
                    </li>
                    <li class="nav-item">
                        <form style="margin-bottom: 0px" method="post"
                              action="${pageContext.request.contextPath}/mainController">
                            <fmt:message var="orders" key="main.orders"/>
                            <input type="hidden" name="command" value="show_all_orders">
                            <button class="btn btn-link nav-link" type="submit">
                                <span data-feather="shopping-cart"></span>
                                ${orders} <span class="sr-only">(current)</span>
                            </button>
                        </form>
                    </li>
                    <li class="nav-item">
                        <form style="margin-bottom: 0px" method="post"
                              action="${pageContext.request.contextPath}/mainController">
                            <fmt:message var="users" key="admin.users.header"/>
                            <input type="hidden" name="command" value="all_users">
                            <button class="btn btn-link nav-link" type="submit">
                                <span data-feather="users"></span>
                                ${users} <span class="sr-only">(current)</span>
                            </button>
                        </form>
                    </li>
                    <li class="nav-item">
                        <form style="margin-bottom: 0px" method="post"
                              action="${pageContext.request.contextPath}/mainController">
                            <fmt:message var="addUser" key="admin.add.user.header"/>
                            <input type="hidden" name="command" value="go_to_add_user">
                            <button class="btn btn-link nav-link" type="submit">
                                <span data-feather="plus-circle"></span>
                                ${addUser} <span class="sr-only">(current)</span>
                            </button>
                        </form>
                    </li>
                </ul>

                <h6 class="sidebar-heading d-flex justify-content-between align-items-center px-3 mt-4 mb-1 text-muted">
                    <fmt:message var="workDrugs" key="admin.drugs.work"/>
                    <span>${workDrugs}</span>
                </h6>
                <ul class="nav flex-column mb-2">
                    <li class="nav-item">
                        <fmt:message var="headerCategory" key="admin.add.category.label"/>
                        <form style="margin-bottom: 0px" method="post"
                              action="${pageContext.request.contextPath}/jsp/admin/addCategory.jsp">
                            <button style="word-break: break-all" class="btn btn-link nav-link" type="submit">
                                <span data-feather="plus-circle"></span>
                                ${headerCategory} <span class="sr-only">(current)</span>
                            </button>
                        </form>
                    </li>
                    <li class="nav-item">
                        <fmt:message var="headerDrug" key="admin.add.drug.label"/>
                        <form style="margin-bottom: 0px" method="post"
                              action="${pageContext.request.contextPath}/mainController">
                            <input type="hidden" name="command" value="go_to_add_drug">
                            <button style="word-break: break-all" class="btn btn-link nav-link" type="submit">
                                <span data-feather="plus-circle"></span>
                                ${headerDrug} <span class="sr-only">(current)</span>
                            </button>
                        </form>
                    </li>
                    <li class="nav-item">
                        <fmt:message var="headerDosage" key="admin.add.dosage.header"/>
                        <form style="margin-bottom: 0px" method="post"
                              action="${pageContext.request.contextPath}/mainController">
                            <input type="hidden" name="command" value="go_to_add_dosage">
                            <button style="word-break: break-all" class="btn btn-link nav-link" type="submit">
                                <span data-feather="plus-circle"></span>
                                ${headerDosage} <span class="sr-only">(current)</span>
                            </button>
                        </form>
                    </li>
                    <li class="nav-item">
                        <form style="margin-bottom: 0px" method="post"
                              action="${pageContext.request.contextPath}/mainController">
                            <fmt:message var="deleteDrug" key="admin.delete.drug.header"/>
                            <input type="hidden" name="command" value="go_to_delete_drug">
                            <button class="btn btn-link nav-link" type="submit">
                                <span data-feather="x-circle"></span>
                                ${deleteDrug} <span class="sr-only">(current)</span>
                            </button>
                        </form>
                    </li>
                    <li class="nav-item">
                        <form style="margin-bottom: 0px" method="post"
                              action="${pageContext.request.contextPath}/mainController">
                            <fmt:message var="deleteCategory" key="admin.add.category.delete"/>
                            <input type="hidden" name="command" value="go_to_delete_category">
                            <button class="btn btn-link nav-link" type="submit">
                                <span data-feather="x-circle"></span>
                                ${deleteCategory} <span class="sr-only">(current)</span>
                            </button>
                        </form>
                    </li>
                    <li class="nav-item">
                        <form style="margin-bottom: 0px" method="post"
                              action="${pageContext.request.contextPath}/mainController">
                            <fmt:message var="goToAllDrugs" key="main.drugs.button"/>
                            <input type="hidden" name="command" value="go_to_all_drugs">
                            <button class="btn btn-link nav-link" type="submit">
                                <span data-feather="list"></span>
                                ${goToAllDrugs} <span class="sr-only">(current)</span>
                            </button>
                        </form>
                    </li>
                </ul>
            </div>
        </nav>
    </div>
</div>

