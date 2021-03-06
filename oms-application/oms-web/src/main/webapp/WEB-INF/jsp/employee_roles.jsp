<nav class="navbar navbar-default navbar-static-top" role="navigation"
	style="margin-bottom: 0">
	<div class="navbar-header">
		<button type="button" class="navbar-toggle" data-toggle="collapse"
			data-target=".navbar-collapse">
			<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span>
			<span class="icon-bar"></span> <span class="icon-bar"></span>
		</button>
		<a class="navbar-brand" href="/home">Office Partner</a>
	</div>
	<!-- /.navbar-header -->

	<ul class="nav navbar-top-links navbar-right">
		<!-- /.dropdown -->
		<li class="dropdown"><a class="dropdown-toggle"
			data-toggle="dropdown" href="#"> <i class="fa fa-user fa-fw"></i>
				<i class="fa fa-caret-down"></i>
		</a>
			<ul class="dropdown-menu dropdown-user">
				<li><a href="#"><i class="fa fa-user fa-fw"></i> User
						Profile</a></li>
				<li><a href="#"><i class="fa fa-gear fa-fw"></i> Settings</a></li>
				<li class="divider"></li>
				<li><a href="${pageContext.request.contextPath}/logout"><i
						class="fa fa-sign-out fa-fw"></i> Logout</a></li>
			</ul> <!-- /.dropdown-user --></li>
		<!-- /.dropdown -->
	</ul>
	<!-- /.navbar-top-links -->

	<div class="navbar-default sidebar" role="navigation">
		<div class="sidebar-nav navbar-collapse">
			<ul class="nav" id="side-menu">

				<li><a href="${pageContext.request.contextPath}/home"><i
						class="fa fa-dashboard fa-fw"></i> Dashboard</a></li>


				<li><a href="#"><i class="fa fa-wrench fa-fw"></i>
						Configuration<span class="fa arrow"></span></a>
					<ul class="nav nav-second-level">
						<li><a href="${pageContext.request.contextPath}/departments">
								Departments </a></li>
						<li><a href="${pageContext.request.contextPath}/categories">
								Category </a></li>
						<li><a href="${pageContext.request.contextPath}/reportingviews">
								Reporting Views </a></li>
					</ul> <!-- /.nav-second-level --></li>



				<li><a href="${pageContext.request.contextPath}/process/tasks">
						<i class="fa fa-edit fa-fw"></i>My Tasks
				</a></li>

				<li><a href="${pageContext.request.contextPath}/stocks"> <i
						class="fa fa-edit fa-fw"></i>My Assets
				</a></li>

				<li><a href="${pageContext.request.contextPath}/requests">
						<i class="fa fa-edit fa-fw"></i>Material Requests
				</a></li>

				<li><a href="${pageContext.request.contextPath}/purchases">
						<i class="fa fa-edit fa-fw"></i>New Purchase Request
				</a></li>

				<li><a href="${pageContext.request.contextPath}/reports"> <i
						class="fa fa-edit fa-fw"></i>Custom Reports
				</a></li>

			</ul>
		</div>
		<!-- /.sidebar-collapse -->
	</div>
	<!-- /.navbar-static-side -->
</nav>