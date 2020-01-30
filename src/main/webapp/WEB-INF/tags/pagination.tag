<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="page" required="true" type="org.springframework.data.domain.Page" %>
<%@ attribute name="url" required="true" %>



<div class="pagination">

    <a href="">&lt;&lt;</a>
    <c:forEach var="pg" begin="1" end="${page.totalPages}">
        <c:choose>
            <c:when test="${page.number != pg -1}">
                <a href="${url}?p=${pg}"><c:out value="${pg}"/></a>
            </c:when>
            <c:otherwise>
                <strong><c:out value="${pg}"/></strong>
            </c:otherwise>
        </c:choose>
        <c:if test="${pg!=page.totalPages}">
            |
        </c:if>
    </c:forEach>
    <a href="">&gt;&gt;</a>

</div>