<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="/webjars/bootstrap/4.5.0/css/bootstrap.min.css" />
<script src="/webjars/jquery/3.5.1/jquery.min.js"></script>
<script src="/webjars/bootstrap/4.5.0/js/bootstrap.min.js"></script>
<title>Events!</title>
</head>
<body>
	<div class="container center mt-5">
		<div class="row">
			<div class="col border border-primary">
				<h3 class="m-5 text-info">
					Welcome to
					<c:out value="${event.name}" />
				</h3>


				<ul class="m-5 text-info">
					<li>Location: <c:out value="${event.location},${event.state}" /></li>
					<li>Date: <c:out value="${event.eventdate}" /></li>
					<li>Host: <c:out value="${event.host.firstName}" /></li>
				</ul>
				<a class="btn btn-primary float-right" href="/logout">Logout</a>

				<h4>Guests</h4>
				<table class="table">
					<thead class="thead-light">
						<th>Name</th>
						<th>State</th>
					</thead>
					<tbody>
						<c:forEach var="attendee" items="${event.attendees}">
							<tr>
								<td>${attendee.firstName} ${attendee.lastName}</td>
								<td>${attendee.state}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>

			<div class="col border border-primary">
				<h2 class="m-5 text-info">Message Wall</h2>
				<p></p>
				<div class="form-group">
					<c:forEach var="message" items="${event.messages}">
						<label for="comments">Comments</label>
						<textarea class="form-control" id="comments" rows="3">
					${message.content}
					</textarea>
					</c:forEach>
				</div>
				<form:errors path="comment.*" />

				<form class="form-group push" method="POST"
					action="/createMessage/${event.id}">


					<div class="form-group">
						<label for="messages">Messages</label>
						<textarea class="form-control" id="content" name="content"
							rows="3"></textarea>
					</div>

					<input class="btn btn-success mt-3" type="submit"
						value="Add comment!" />
				</form>

			</div>
		</div>
	</div>

</body>
</html>