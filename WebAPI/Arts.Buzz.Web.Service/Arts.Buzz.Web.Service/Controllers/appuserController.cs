using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web;
using System.Web.Http;

namespace Arts.Buzz.Web.Service.Controllers
{
    public class appuserController : BaseApiController
    {
        // GET api/appuser
        public IEnumerable<appuser> Getappusers()
        {
            return db.appuser.AsEnumerable();
        }

        // GET api/appuser/5
        public appuser Getappuser(string id)
        {
            appuser appuser = db.appuser.Find(id);
            if (appuser == null)
            {
                throw new HttpResponseException(Request.CreateResponse(HttpStatusCode.NotFound));
            }

            return appuser;
        }

        // PUT api/appuser/5
        public HttpResponseMessage Putappuser(string id, appuser appuser)
        {
            if (!ModelState.IsValid)
            {
                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, ModelState);
            }

            if (id != appuser.userid)
            {
                return Request.CreateResponse(HttpStatusCode.BadRequest);
            }

            db.Entry(appuser).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException ex)
            {
                return Request.CreateErrorResponse(HttpStatusCode.NotFound, ex);
            }

            return Request.CreateResponse(HttpStatusCode.OK);
        }

        // POST api/appuser
        public HttpResponseMessage Postappuser(appuser appuser)
        {
            if (ModelState.IsValid)
            {
                db.appuser.Add(appuser);
                db.SaveChanges();

                HttpResponseMessage response = Request.CreateResponse(HttpStatusCode.Created, appuser);
                response.Headers.Location = new Uri(Url.Link("DefaultApi", new { id = appuser.userid }));
                return response;
            }
            else
            {
                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, ModelState);
            }
        }

        // DELETE api/appuser/5
        public HttpResponseMessage Deleteappuser(string id)
        {
            appuser appuser = db.appuser.Find(id);
            if (appuser == null)
            {
                return Request.CreateResponse(HttpStatusCode.NotFound);
            }

            db.appuser.Remove(appuser);

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException ex)
            {
                return Request.CreateErrorResponse(HttpStatusCode.NotFound, ex);
            }

            return Request.CreateResponse(HttpStatusCode.OK, appuser);
        }

        protected override void Dispose(bool disposing)
        {
            db.Dispose();
            base.Dispose(disposing);
        }
    }
}