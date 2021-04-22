<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Welcome</title>

<link rel="stylesheet"
	href="/webjars/bootstrap/4.5.0/css/bootstrap.min.css" />
<script src="/webjars/jquery/3.5.1/jquery.min.js"></script>
<script src="/webjars/bootstrap/4.5.0/js/bootstrap.min.js"></script>
</head>
<body>
	<h1 class="m-5 text-info">
		Welcome
		<c:out value="${user.firstName}" />
	</h1>
	<div class="container center mt-5">
		<div class="row">
			<div class="col border border-primary">
				<h2>Events in your area:</h2>
				<table class="table col-6 align-center">
					<thead>
						<tr>
							<th scope="col">Name of Event</th>
							<th scope="col">Location</th>
							<th scope="col">Date</th>
							<th scope="col">Host</th>
							<th scope="col">Action/Status</th>
						</tr>
					</thead>
					<tbody>

						<c:forEach items="${events}" var="event">
							<c:if test="${user.state == event.state}">

								<tr>
									<td><a href="/showEvent/${event.id}">${event.name}</a></td>
									<td>${event.location},${event.state}</td>
									<td>${event.eventdate}</td>
									<td>${event.host.firstName}</td>

									<td><a href="/events/${id}">Edit</a> | <a
										href="/delete/${event.id}">Delete</a>
								</tr>
							</c:if>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<div class="row">
			<div class="col border border-primary">
				<h2>Events in other areas:</h2>
				<table class="table col-6 align-center">
					<thead>
						<tr>
							<th scope="col">Name of Event</th>
							<th scope="col">Location</th>
							<th scope="col">Date</th>
							<th scope="col">Host</th>
							<th scope="col">Action/Status</th>
						</tr>
					</thead>
					<tbody>

						<c:forEach items="${events}" var="event">
						<c:if test="${user.state != event.state}">


							<tr>

								<td><a href="/showEvent/${event.id}">${event.name}</a></td>
								<td>${event.location},${event.state}</td>
								<td>${event.eventdate}</td>
								<td>${event.host.firstName}</td>

								<c:choose>
									<c:when test="${event.host.id == user.id }">

										<td><a href="/events/${event.id}/edit">Edit</a> | <a
											href="/delete/${event.id}">Delete</a></td>
									</c:when>

									<c:otherwise>
										<c:set var="attending" value="${false}" />
										<c:forEach items="${event.getAttendees()}" var="guest">
											<c:if test="${guest == user}">
												<c:set var="attending" value="${true}" />
											</c:if>
										</c:forEach>
										<c:choose>
											<c:when test="${attending == false}">
												<td><a href="/events/addUser/${event.id}">Join |</a></td>
											</c:when>
											<c:otherwise>
       <td><a href="/events/removeUser/${event.id}">Cancel</a></td>
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
							</tr>




						</c:if>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<h3>Create Event</h3>
		<p>
			<form:errors path="event.*" />
		</p>

		<form:form class="form-group push" method="POST" action="/createEvent"
			modelAttribute="event">
			<p>
				<form:label path="name">Name:</form:label>
				<form:input type="name" path="name" />
			</p>
			<p>
				<form:label path="location">Location:</form:label>
				<form:input type="location" path="location" />
				<form:select name="state" id="state" path="state">
					<option value="AL">Alabama</option>
					<option value="AK">Alaska</option>
					<option value="AZ">Arizona</option>
					<option value="AR">Arkansas</option>
					<option value="CA">California</option>
					<option value="CO">Colorado</option>
					<option value="CT">Connecticut</option>
					<option value="DE">Delaware</option>
					<option value="DC">District Of Columbia</option>
					<option value="FL">Florida</option>
					<option value="GA">Georgia</option>
					<option value="HI">Hawaii</option>
					<option value="ID">Idaho</option>
					<option value="IL">Illinois</option>
					<option value="IN">Indiana</option>
					<option value="IA">Iowa</option>
					<option value="KS">Kansas</option>
					<option value="KY">Kentucky</option>
					<option value="LA">Louisiana</option>
					<option value="ME">Maine</option>
					<option value="MD">Maryland</option>
					<option value="MA">Massachusetts</option>
					<option value="MI">Michigan</option>
					<option value="MN">Minnesota</option>
					<option value="MS">Mississippi</option>
					<option value="MO">Missouri</option>
					<option value="MT">Montana</option>
					<option value="NE">Nebraska</option>
					<option value="NV">Nevada</option>
					<option value="NH">New Hampshire</option>
					<option value="NJ">New Jersey</option>
					<option value="NM">New Mexico</option>
					<option value="NY">New York</option>
					<option value="NC">North Carolina</option>
					<option value="ND">North Dakota</option>
					<option value="OH">Ohio</option>
					<option value="OK">Oklahoma</option>
					<option value="OR">Oregon</option>
					<option value="PA">Pennsylvania</option>
					<option value="RI">Rhode Island</option>
					<option value="SC">South Carolina</option>
					<option value="SD">South Dakota</option>
					<option value="TN">Tennessee</option>
					<option value="TX">Texas</option>
					<option value="UT">Utah</option>
					<option value="VT">Vermont</option>
					<option value="VA">Virginia</option>
					<option value="WA">Washington</option>
					<option value="WV">West Virginia</option>
					<option value="WI">Wisconsin</option>
					<option value="WY">Wyoming</option>
				</form:select>
			</p>
			<p>
				<form:label path="eventdate">Date:</form:label>
				<form:input type="date" path="eventdate" />
			</p>


			<input class="btn btn-success mt-3" type="submit"
				value="Create Event!" />
			<a class="btn btn-primary float-right" href="/logout">Logout</a>
		</form:form>
	</div>
	<!-- </div>
	</div> -->


</body>
</html>